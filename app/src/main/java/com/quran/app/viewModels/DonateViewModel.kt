package com.quran.app.viewModels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.quran.app.BuildConfig
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender
import com.solana.mobilewalletadapter.clientlib.ConnectionIdentity
import com.solana.mobilewalletadapter.clientlib.MobileWalletAdapter
import com.solana.mobilewalletadapter.clientlib.TransactionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.sol4k.Base58
import org.sol4k.Connection
import org.sol4k.PublicKey
import org.sol4k.TransactionMessage
import org.sol4k.instruction.TransferInstruction

class DonateViewModel(application: Application) : AndroidViewModel(application) {

    sealed interface WalletState {
        object Disconnected : WalletState
        object Connecting : WalletState
        object NoWalletApp : WalletState
        data class ConnectError(val message: String) : WalletState
        data class Connected(
            val address: String,
            val fullAddress: String,
            val balanceLamports: Long = 0L
        ) : WalletState
    }

    sealed interface TransactionState {
        object Idle : TransactionState
        object Preparing : TransactionState
        object Signing : TransactionState
        object Confirming : TransactionState
        data class Success(val signature: String) : TransactionState
        data class Error(val message: String) : TransactionState
    }

    // SharedPreferences for persistent session
    private val prefs = application.getSharedPreferences("quran_donate_prefs", Context.MODE_PRIVATE)

    var authToken: String?
        get() = prefs.getString("mwa_auth_token", null)
        private set(value) {
            prefs.edit().apply {
                if (value != null) putString("mwa_auth_token", value)
                else remove("mwa_auth_token")
                apply()
            }
        }

    var savedWalletAddress: String?
        get() = prefs.getString("mwa_wallet_address", null)
        private set(value) {
            prefs.edit().apply {
                if (value != null) putString("mwa_wallet_address", value)
                else remove("mwa_wallet_address")
                apply()
            }
        }

    // Network Selection
    var selectedNetwork by mutableStateOf(if (BuildConfig.DEBUG) "solana:devnet" else "solana:mainnet-beta")
        private set

    val solanaChain: String
        get() = if (BuildConfig.DEBUG) selectedNetwork else "solana:mainnet-beta"

    val rpcEndpoints: List<String>
        get() = if (solanaChain == "solana:devnet") {
            listOf(
                "https://api.devnet.solana.com",
                "https://rpc.ankr.com/solana_devnet"
            )
        } else {
            listOf(
                "https://api.mainnet-beta.solana.com",
                "https://rpc.ankr.com/solana",
                "https://solana.publicnode.com"
            )
        }

    val activeRpcUrl: String
        get() = rpcEndpoints.first()

    val recipientAddress: String = "DJAPiUv8vCzEUJSGSwTW1hEiKs1GmXqDW8uNC477KkUh"

    // UI States
    var walletState by mutableStateOf<WalletState>(WalletState.Disconnected)
        private set

    var txState by mutableStateOf<TransactionState>(TransactionState.Idle)
        private set

    var selectedPreset by mutableStateOf<Double?>(0.1)
        internal set

    var customAmountText by mutableStateOf("")
        internal set

    // MWA Setup
    val identity = ConnectionIdentity(
        identityUri = Uri.parse("https://web-quran-kareem.vercel.app"),
        iconUri = Uri.parse("favicon.ico"),
        identityName = "Quran App"
    )

    val walletAdapter = MobileWalletAdapter(connectionIdentity = identity)

    init {
        // Restore saved session
        val cachedAddress = savedWalletAddress
        if (!cachedAddress.isNullOrEmpty()) {
            val truncated = "${cachedAddress.take(4)}...${cachedAddress.takeLast(4)}"
            walletState = WalletState.Connected(address = truncated, fullAddress = cachedAddress, balanceLamports = 0L)
            fetchBalanceAndSetState(cachedAddress)
        }
    }

    fun toggleNetwork(network: String) {
        if (selectedNetwork != network) {
            selectedNetwork = network
            disconnectWallet()
        }
    }

    fun connectWallet(sender: ActivityResultSender) {
        walletState = WalletState.Connecting
        viewModelScope.launch {
            try {
                var result = walletAdapter.transact(sender) {
                    authorize(
                        identityUri = identity.identityUri,
                        iconUri = identity.iconUri,
                        identityName = identity.identityName,
                        chain = solanaChain
                    )
                }

                if (result is TransactionResult.Failure && walletAdapter.authToken != null) {
                    disconnectWallet()
                    walletState = WalletState.Connecting
                    result = walletAdapter.transact(sender) {
                        authorize(
                            identityUri = identity.identityUri,
                            iconUri = identity.iconUri,
                            identityName = identity.identityName,
                            chain = solanaChain
                        )
                    }
                }

                when (result) {
                    is TransactionResult.Success -> {
                        val publicKeyBytes = result.payload.accounts.firstOrNull()?.publicKey ?: ByteArray(0)
                        val pk = Base58.encode(publicKeyBytes)
                        val truncated = "${pk.take(4)}...${pk.takeLast(4)}"

                        authToken = result.payload.authToken
                        savedWalletAddress = pk

                        walletState = WalletState.Connected(address = truncated, fullAddress = pk, balanceLamports = 0L)
                        fetchBalanceAndSetState(pk)
                    }
                    is TransactionResult.NoWalletFound -> {
                        disconnectWallet()
                        walletState = WalletState.NoWalletApp
                    }
                    is TransactionResult.Failure -> {
                        disconnectWallet()
                        walletState = WalletState.ConnectError(getFriendlyErrorMessage(result.e))
                    }
                }
            } catch (e: Exception) {
                Log.e("DonateViewModel", "Connect error", e)
                disconnectWallet()
                walletState = WalletState.ConnectError(getFriendlyErrorMessage(e))
            }
        }
    }

    fun disconnectWallet() {
        walletState = WalletState.Disconnected
        txState = TransactionState.Idle
        authToken = null
        savedWalletAddress = null
    }

    fun fetchBalanceAndSetState(fullAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var fetchedBalance = 0L
            for (url in rpcEndpoints) {
                try {
                    val conn = Connection(url)
                    val balanceBigInt = conn.getBalance(PublicKey(fullAddress))
                    fetchedBalance = balanceBigInt.toLong()
                    break
                } catch (e: Exception) {
                    Log.w("DonateViewModel", "Failed to query balance from $url", e)
                }
            }

            withContext(Dispatchers.Main) {
                val current = walletState
                if (current is WalletState.Connected && current.fullAddress == fullAddress) {
                    walletState = current.copy(balanceLamports = fetchedBalance)
                }
            }
        }
    }

    fun executeTip(sender: ActivityResultSender, amountSol: Double) {
        val activeWallet = walletState as? WalletState.Connected ?: return
        txState = TransactionState.Preparing

        viewModelScope.launch {
            try {
                // 1. Establish RPC Connection & Fetch fresh blockhash
                var connection: Connection? = null
                var usedRpcUrl = activeRpcUrl

                withContext(Dispatchers.IO) {
                    for (url in rpcEndpoints) {
                        try {
                            val conn = Connection(url)
                            conn.getLatestBlockhash()
                            connection = conn
                            usedRpcUrl = url
                            break
                        } catch (e: Exception) {
                            Log.w("DonateViewModel", "Failed to connect to RPC $url", e)
                        }
                    }
                }

                if (connection == null) {
                    connection = Connection(usedRpcUrl)
                }
                val conn = connection!!
                val lamports = java.math.BigDecimal(amountSol.toString())
                    .multiply(java.math.BigDecimal(1_000_000_000L))
                    .toLong()

                if (lamports <= 0L) {
                    txState = TransactionState.Error("Invalid donation amount.")
                    return@launch
                }

                // Check Balance
                val hasEnoughBalance = withContext(Dispatchers.IO) {
                    try {
                        val balance = conn.getBalance(PublicKey(activeWallet.fullAddress))
                        val required = java.math.BigInteger.valueOf(lamports) + java.math.BigInteger.valueOf(5000L)
                        balance >= required
                    } catch (e: Exception) {
                        Log.e("DonateViewModel", "Failed to query balance before tip", e)
                        true
                    }
                }

                if (!hasEnoughBalance) {
                    txState = TransactionState.Error("Insufficient SOL balance for this transaction.")
                    return@launch
                }

                txState = TransactionState.Signing

                val destination = PublicKey(recipientAddress)
                val currentAuthToken = authToken
                val senderFullAddress = activeWallet.fullAddress

                val result = withContext(Dispatchers.IO) {
                    walletAdapter.transact(sender) {
                        if (!currentAuthToken.isNullOrEmpty()) {
                            reauthorize(
                                identityUri = identity.identityUri,
                                iconUri = identity.iconUri,
                                identityName = identity.identityName,
                                authToken = currentAuthToken
                            )
                        } else {
                            authorize(
                                identityUri = identity.identityUri,
                                iconUri = identity.iconUri,
                                identityName = identity.identityName,
                                chain = solanaChain
                            )
                        }

                        val senderKey = PublicKey(Base58.decode(senderFullAddress))
                        val freshBlockhash = conn.getLatestBlockhash()
                        val instruction = TransferInstruction(senderKey, destination, lamports)
                        val message = TransactionMessage.newMessage(senderKey, freshBlockhash, instruction)

                        val unsignedTx = byteArrayOf(1) + ByteArray(64) + message.serialize()
                        val signResult = signTransactions(arrayOf(unsignedTx))
                        val signedTxs = signResult.signedPayloads
                        if (signedTxs.isEmpty()) {
                            throw IllegalStateException("Wallet did not return signed transaction payload.")
                        }

                        val signedTxBytes = signedTxs.first()
                        sendSignedTransaction(usedRpcUrl, signedTxBytes)
                    }
                }

                when (result) {
                    is TransactionResult.Success -> {
                        val signature = result.payload
                        walletAdapter.authToken?.let { authToken = it }

                        txState = TransactionState.Confirming

                        val confirmed = confirmTransaction(usedRpcUrl, signature)
                        if (confirmed) {
                            txState = TransactionState.Success(signature)
                            fetchBalanceAndSetState(activeWallet.fullAddress)
                        } else {
                            txState = TransactionState.Error("Transaction broadcasted (Sig: ${signature.take(8)}...), but confirmation timed out. Please check Solscan.")
                        }
                    }
                    is TransactionResult.NoWalletFound -> {
                        txState = TransactionState.Error("No compatible Solana wallet app (Phantom/Solflare) found.")
                    }
                    is TransactionResult.Failure -> {
                        if (result.e.message?.contains("authorization", ignoreCase = true) == true) {
                            disconnectWallet()
                        }
                        txState = TransactionState.Error(getFriendlyErrorMessage(result.e))
                    }
                }

            } catch (e: Exception) {
                Log.e("DonateViewModel", "Transaction error", e)
                txState = TransactionState.Error(getFriendlyErrorMessage(e))
            }
        }
    }

    fun resetTxState() {
        txState = TransactionState.Idle
    }

    private fun getFriendlyErrorMessage(e: Throwable): String {
        val message = e.message ?: ""
        return when {
            e is java.util.concurrent.CancellationException ||
                e is kotlinx.coroutines.CancellationException ||
                message.contains("cancelled", ignoreCase = true) ||
                message.contains("canceled", ignoreCase = true) ||
                message.contains("declined", ignoreCase = true) -> {
                "Connection or transaction was canceled by user."
            }
            e is java.net.ConnectException ||
                message.contains("ECONNREFUSED", ignoreCase = true) ||
                message.contains("failed to connect", ignoreCase = true) ||
                message.contains("Failed to connect", ignoreCase = true) -> {
                "Unable to connect to wallet. Make sure a Solana wallet app (Phantom or Solflare) is installed and open."
            }
            else -> message.ifEmpty { "An error occurred while processing the transaction." }
        }
    }

    private suspend fun sendSignedTransaction(rpcUrl: String, signedTxBytes: ByteArray): String = withContext(Dispatchers.IO) {
        val url = java.net.URL(rpcUrl)
        val base64Tx = android.util.Base64.encodeToString(signedTxBytes, android.util.Base64.NO_WRAP)
        val connection = url.openConnection() as java.net.HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        val body = """{"jsonrpc":"2.0","id":1,"method":"sendTransaction","params":["$base64Tx",{"encoding":"base64","preflightCommitment":"confirmed"}]}"""
        connection.outputStream.use { it.write(body.toByteArray()) }
        val response = connection.inputStream.use { it.bufferedReader().readText() }
        connection.disconnect()

        val json = org.json.JSONObject(response)
        if (json.has("error")) {
            val err = json.getJSONObject("error")
            val errMsg = err.optString("message", "Transaction broadcast failed")
            throw IllegalStateException("RPC Error: $errMsg")
        }
        return@withContext json.getString("result")
    }

    private suspend fun confirmTransaction(rpcUrl: String, signature: String): Boolean = withContext(Dispatchers.IO) {
        val url = java.net.URL(rpcUrl)
        var attempts = 0
        while (attempts < 20) {
            delay(2000)
            attempts++
            try {
                val connection = url.openConnection() as java.net.HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true
                val body = """{"jsonrpc":"2.0","id":1,"method":"getSignatureStatuses","params":[["$signature"],{"searchTransactionHistory":true}]}"""
                connection.outputStream.use { it.write(body.toByteArray()) }
                val response = connection.inputStream.use { it.bufferedReader().readText() }
                connection.disconnect()

                val json = org.json.JSONObject(response)
                if (json.has("result")) {
                    val resObj = json.getJSONObject("result")
                    val valueArr = resObj.optJSONArray("value")
                    if (valueArr != null && valueArr.length() > 0 && !valueArr.isNull(0)) {
                        val statusObj = valueArr.getJSONObject(0)
                        if (!statusObj.isNull("err")) {
                            return@withContext false
                        }
                        val status = statusObj.optString("confirmationStatus", "")
                        if (status == "confirmed" || status == "finalized") {
                            return@withContext true
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("DonateViewModel", "Confirmation check error", e)
            }
        }
        false
    }

    fun lamportsToSol(lamports: Long): String {
        val sol = lamports.toDouble() / 1_000_000_000.0
        return String.format(java.util.Locale.US, "%.4f", sol)
    }
}
