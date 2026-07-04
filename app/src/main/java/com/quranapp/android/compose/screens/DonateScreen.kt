package com.quranapp.android.compose.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quranapp.android.R
import com.quranapp.android.compose.components.common.AppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.net.Uri
import android.util.Log
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender
import com.solana.mobilewalletadapter.clientlib.MobileWalletAdapter
import com.solana.mobilewalletadapter.clientlib.ConnectionIdentity
import com.solana.mobilewalletadapter.clientlib.TransactionResult
import com.solana.mobilewalletadapter.clientlib.RpcCluster
import androidx.activity.ComponentActivity
import org.sol4k.Connection
import org.sol4k.PublicKey
import org.sol4k.RpcUrl
import org.sol4k.TransactionMessage
import org.sol4k.VersionedTransaction
import org.sol4k.instruction.TransferInstruction
import androidx.activity.compose.LocalActivityResultRegistryOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateScreen(activityResultSender: ActivityResultSender) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isWalletConnected by remember { mutableStateOf(false) }
    var connectedWalletAddress by remember { mutableStateOf("") }
    var selectedAmount by remember { mutableStateOf("0.1") }
    var customAmount by remember { mutableStateOf("") }
    var isCustomAmount by remember { mutableStateOf(false) }
    var isTransacting by remember { mutableStateOf(false) }
    var authToken by remember { mutableStateOf<String?>(null) }
    
    // MWA Setup
    val identity = remember {
        ConnectionIdentity(
            identityUri = Uri.parse("https://quranapp.com"),
            iconUri = Uri.parse("favicon.ico"),
            identityName = "Quran App"
        )
    }
    val walletAdapter = remember { MobileWalletAdapter(connectionIdentity = identity) }
    val activityResultRegistryOwner = LocalActivityResultRegistryOwner.current

    val amounts = listOf("0.1", "0.5", "1.0")

    Scaffold(
        topBar = { AppBar(stringResource(R.string.donate)) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.dr_logo),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Support Our Journey",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = "Your contribution helps us keep the app ad-free and continuously improve features. Donate directly via Solana blockchain.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Wallet Connection Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isWalletConnected) {
                    Text(
                        text = "Connected Wallet:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = connectedWalletAddress,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedButton(
                        onClick = {
                            isWalletConnected = false
                            connectedWalletAddress = ""
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Disconnect")
                    }
                } else {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    val result = walletAdapter.transact(activityResultSender) {
                                        val authResult = authorize(
                                            identityUri = identity.identityUri,
                                            iconUri = identity.iconUri,
                                            identityName = identity.identityName,
                                            rpcCluster = RpcCluster.MainnetBeta
                                        )
                                        authResult
                                    }
                                    if (result is TransactionResult.Success) {
                                        val pk = org.sol4k.Base58.encode(result.payload?.publicKey ?: ByteArray(0))
                                        isWalletConnected = true
                                        connectedWalletAddress = "${pk.take(4)}...${pk.takeLast(4)}"
                                        authToken = result.payload?.authToken
                                        Toast.makeText(context, "Wallet connected", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Connection failed", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Log.e("DonateScreen", "Connect error", e)
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Connect Solana Wallet",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (isWalletConnected) {
                // Amount Selection Section
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Select Amount (SOL)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        amounts.forEach { amount ->
                            AmountCard(
                                amount = amount,
                                isSelected = !isCustomAmount && selectedAmount == amount,
                                modifier = Modifier.weight(1f)
                            ) {
                                isCustomAmount = false
                                selectedAmount = amount
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = isCustomAmount,
                            onClick = { isCustomAmount = true }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = customAmount,
                            onValueChange = { customAmount = it },
                            placeholder = { Text("Custom Amount") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            enabled = isCustomAmount,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }

                    Button(
                        onClick = {
                            val amountToSend = if (isCustomAmount) customAmount else selectedAmount
                            if (amountToSend.isNotBlank()) {
                                val amountDouble = amountToSend.toDoubleOrNull()
                                if (amountDouble != null && amountDouble > 0) {
                                    isTransacting = true
                                    Toast.makeText(context, "Initiating transaction...", Toast.LENGTH_SHORT).show()
                                    
                                    coroutineScope.launch {
                                        try {
                                            // 1. Get recent blockhash using sol4k
                                            val connection = Connection("https://api.mainnet-beta.solana.com")
                                            val blockhash = withContext(Dispatchers.IO) { connection.getLatestBlockhash() }
                                            
                                            // 2. Build Transaction
                                            val destination = PublicKey("9Lu9xyeA1DnLDz6ERBFxCAaoPt1G3UoUKPjT4BimdCWr")
                                            val lamports = (amountDouble * 1_000_000_000).toLong()
                                            
                                            // 3. We will sign it via MWA transact
                                            val result = walletAdapter.transact(activityResultSender) {
                                                val authResult = reauthorize(
                                                    identityUri = identity.identityUri,
                                                    iconUri = identity.iconUri,
                                                    identityName = identity.identityName,
                                                    authToken = authToken ?: ""
                                                )
                                                
                                                val senderKey = PublicKey(authResult.publicKey)
                                                val instruction = TransferInstruction(senderKey, destination, lamports)
                                                val message = TransactionMessage.newMessage(senderKey, blockhash, instruction)
                                                val tx = VersionedTransaction(message).serialize()
                                                
                                                signAndSendTransactions(arrayOf(tx))
                                            }
                                            
                                            isTransacting = false
                                            if (result is TransactionResult.Success) {
                                                Toast.makeText(context, "Donation Sent! Thank You.", Toast.LENGTH_LONG).show()
                                            } else {
                                                Toast.makeText(context, "Transaction Failed or Canceled", Toast.LENGTH_SHORT).show()
                                            }
                                            
                                        } catch (e: Exception) {
                                            isTransacting = false
                                            Log.e("DonateScreen", "Transact error", e)
                                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Please enter a valid positive amount", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !isTransacting,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(
                            text = "Send Donation",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AmountCard(
    amount: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$amount SOL",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
