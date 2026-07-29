/*
 * (c) Faisal Khan. Created on 21/11/2021.
 */
package com.quran.app.utils.app

import android.content.Context
import android.content.DialogInterface
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.peacedesign.android.utils.AppBridge
import com.quran.app.R
import com.quran.app.api.models.AppUrls
import com.quran.app.utils.Logger
import com.quran.app.utils.univ.MessageUtils
import com.quran.app.widgets.dialog.loader.PeaceProgressDialog
import java.util.concurrent.CancellationException

object InfoUtils {
    @JvmStatic
    fun openFeedbackPage(context: Context) {
        openTab(context, UrlsManager.URL_KEY_FEEDBACK)
    }

    @JvmStatic
    fun openPrivacyPolicy(context: Context) {
        AppBridge.newOpener(context).browseLink("https://web-quran-kareem.vercel.app/privacy")
    }

    @JvmStatic
    fun openAbout(context: Context) {
        openTab(context, UrlsManager.URL_KEY_ABOUT)
    }

    @JvmStatic
    fun openHelp(context: Context) {
        openTab(context, UrlsManager.URL_KEY_HELP)
    }

    @JvmStatic
    fun openDiscord(context: Context) {
        openTab(context, UrlsManager.URL_KEY_DISCORD)
    }

    @JvmStatic
    fun openDonationLink(context: Context) {
        openTab(context, UrlsManager.URL_KEY_DONATION)
    }

    private fun openTab(context: Context, urlKey: String) {
        val urlsManager = UrlsManager(context)
        val dialog = PeaceProgressDialog(context).apply {
            setMessage(R.string.strTextPleaseWait)
            setButton(
                DialogInterface.BUTTON_NEUTRAL,
                context.getString(R.string.strLabelCancel)
            ) { _, _ ->
                urlsManager.cancel()
                dismiss()
            }
            show()
        }

        val failedCallback = { e: Exception ->
            e.printStackTrace()
            dialog.dismiss()
            if (e !is CancellationException) {
                Logger.reportError(e)
                MessageUtils.popMessage(
                    context,
                    context.getString(R.string.strMsgSomethingWrong),
                    "${context.getString(R.string.strMsgCouldNotOpenPage)} ${
                        context.getString(
                            R.string.strMsgTryLater
                        )
                    }",
                    context.getString(R.string.strLabelClose),
                    null
                )
            }
        }

        urlsManager.getUrlsJson({ (_, about, help, feedback, discord, donation): AppUrls ->
            val url: String? = when (urlKey) {
                UrlsManager.URL_KEY_FEEDBACK -> feedback
                UrlsManager.URL_KEY_ABOUT -> about
                UrlsManager.URL_KEY_HELP -> help
                UrlsManager.URL_KEY_DISCORD -> discord
                UrlsManager.URL_KEY_DONATION -> donation
                else -> null
            }

            if (url.isNullOrEmpty()) {
                dialog.dismiss()
                return@getUrlsJson
            }

            try {
                prepareCustomTab(context).launchUrl(context, url.toUri())
                dialog.dismiss()
            } catch (e: Exception) {
                failedCallback(e)
            }
        }, failedCallback)
    }

    private fun prepareCustomTab(context: Context): CustomTabsIntent {
        val colorSchemeParams = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorBGPage))
            .setNavigationBarColor(ContextCompat.getColor(context, R.color.colorBGPage))
            .build()

        return CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(colorSchemeParams)
            .setShowTitle(true)
            .setUrlBarHidingEnabled(true)
            .build()
    }
}
