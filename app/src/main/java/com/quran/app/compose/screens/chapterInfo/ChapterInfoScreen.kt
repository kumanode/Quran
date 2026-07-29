package com.quran.app.compose.screens.chapterInfo

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.quran.app.R
import com.quran.app.compose.components.common.AppBar
import com.quran.app.compose.components.reader.dialogs.QuickReference
import com.quran.app.compose.components.reader.dialogs.QuickReferenceData
import com.quran.app.compose.components.reader.dialogs.QuickReferenceVerses
import com.quran.app.compose.components.reader.navigator.ChapterVerseNavigator
import com.quran.app.compose.utils.LocalAppLocale
import com.quran.app.compose.utils.preferences.ReaderPreferences
import com.quran.app.db.entities.quran.RevelationType
import com.quran.app.utils.Log
import com.quran.app.utils.Logger
import com.quran.app.utils.chapterInfo.ChapterInfoJSInterface
import com.quran.app.utils.chapterInfo.ChapterInfoWebViewClient
import com.quran.app.utils.reader.factory.ReaderFactory
import com.quran.app.viewModels.ChapterInfoContentState
import com.quran.app.viewModels.ChapterInfoEvent
import com.quran.app.viewModels.ChapterInfoUiState
import com.quran.app.viewModels.ChapterInfoViewModel

data class ChapterInfoContentData(
    val chapterNo: Int,
    val chapterName: String,
    val language: String,
    val verseCount: Int,
    val rukuCount: Int,
    val revelationOrder: Int,
    val revelationType: RevelationType,
    val juzNos: List<Int>,
)


@Composable
fun ChapterInfoScreen(
    initialChapterNo: Int,
    initialLanguage: String?,
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<ChapterInfoViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    LaunchedEffect(initialChapterNo, initialLanguage) {
        onEvent(ChapterInfoEvent.Init(initialChapterNo, initialLanguage))
    }

    var quickRefData by remember { mutableStateOf<QuickReferenceData?>(null) }
    var showChapterNavigator by rememberSaveable { mutableStateOf(false) }
    val translationSlugs = ReaderPreferences.observeTranslations()

    Scaffold(
        modifier = modifier,
        topBar = {
            ChapterInfoTopBar(
                uiState = uiState,
                onOpenChapterNavigator = { showChapterNavigator = true },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val contentState = uiState.contentState) {
                is ChapterInfoContentState.Loading -> LoadingContent()

                is ChapterInfoContentState.Success -> {
                    ChapterInfoWebViewContent(
                        uiState = uiState,
                        contentState = contentState,
                        onOpenReference = { chapterNo, fromVerse, toVerse ->
                            quickRefData = QuickReferenceData(
                                slugs = translationSlugs,
                                chapterNo = chapterNo,
                                parsedVerses = QuickReferenceVerses.Range(
                                    chapterNo,
                                    fromVerse..toVerse
                                ),
                            )
                        },
                    )
                }

                is ChapterInfoContentState.Error -> {
                    ErrorContent(
                        message = contentState.message,
                        canRetry = contentState.canRetry,
                        onRetry = { onEvent(ChapterInfoEvent.Retry) },
                    )
                }

                is ChapterInfoContentState.NoInternet -> {
                    NoInternetContent(
                        onRetry = { onEvent(ChapterInfoEvent.Retry) },
                    )
                }

                is ChapterInfoContentState.InvalidParams -> {
                    InvalidParamsContent()
                }
            }
        }
    }

    ChapterVerseNavigator(
        isOpen = showChapterNavigator,
        onDismiss = { showChapterNavigator = false },
        selectedChapterNo = uiState.chapterNo.takeIf { it >= 1 },
        selectedVerseNos = emptySet(),
        onChapterSelected = { chapterNo ->
            onEvent(ChapterInfoEvent.Init(chapterNo, initialLanguage))
        },
    )

    val context = LocalContext.current
    QuickReference(
        data = quickRefData,
        onOpenInReader = { chapterNo, range ->
            quickRefData = null
            ReaderFactory.startVerseRange(context as Activity, chapterNo, range.first, range.last)
        },
        onClose = { quickRefData = null },
    )
}

@Composable
private fun ChapterInfoTopBar(
    uiState: ChapterInfoUiState,
    onOpenChapterNavigator: () -> Unit,
) {
    val appLocale = LocalAppLocale.current
    val chapterName = uiState.swl?.let { it.getCurrentName() }.orEmpty()
    val chapterNo = uiState.chapterNo

    AppBar(
        title = stringResource(R.string.strTitleAboutSurah),
        actions = {
            TextButton(onOpenChapterNavigator) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (chapterNo >= 1) {
                            if (chapterName.isNotEmpty()) {
                                String.format(
                                    appLocale.platformLocale,
                                    $$"%1$d. %2$s",
                                    chapterNo,
                                    chapterName,
                                )
                            } else {
                                String.format(appLocale.platformLocale, "%d", chapterNo)
                            }
                        } else {
                            ""
                        },
                        fontWeight = FontWeight.SemiBold,
                        color = colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )

                    Icon(
                        painter = painterResource(R.drawable.dr_icon_chevron_down),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun ChapterInfoWebViewContent(
    uiState: ChapterInfoUiState,
    contentState: ChapterInfoContentState.Success,
    onOpenReference: (Int, Int, Int) -> Unit,
) {
    val context = LocalContext.current
    val resources = LocalResources.current
    val appLocale = LocalAppLocale.current

    val contentData =
        remember(uiState.chapterNo, uiState.swl, uiState.juzNos, contentState.langCode, appLocale) {
            val meta = uiState.swl ?: return@remember null
            ChapterInfoContentData(
                uiState.chapterNo,
                resources.getString(
                    R.string.strLabelSurah,
                    meta.getCurrentName()
                ),
                contentState.langCode,
                meta.surah.ayahCount,
                meta.surah.rukusCount,
                meta.surah.revelationOrder,
                meta.surah.revelationType,
                uiState.juzNos,
            )
        } ?: return

    var isLoading by remember { mutableStateOf(true) }
    var lastLoad by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    setBackgroundColor(0x00000000)
                    settings.javaScriptEnabled = true
                    settings.allowUniversalAccessFromFileURLs = true
                    settings.allowFileAccess = true
                    settings.domStorageEnabled = true
                    overScrollMode = View.OVER_SCROLL_NEVER

                    addJavascriptInterface(
                        ChapterInfoJSInterface(
                            context,
                            contentData.verseCount,
                            onOpenReference,
                        ),
                        "ChapterInfoJSInterface"
                    )

                    webChromeClient = object : WebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                            val msg =
                                "[${consoleMessage.lineNumber()}] ${consoleMessage.message()}"
                            Log.d(msg)
                            Logger.logMsg(msg)
                            return true
                        }
                    }
                }
            },
            update = { webView ->
                webView.webViewClient = ChapterInfoWebViewClient(contentData) {
                    isLoading = false
                }

                val html = contentState.html

                if (lastLoad != html) {
                    isLoading = false

                    lastLoad = html
                    webView.loadDataWithBaseURL(
                        null,
                        html,
                        "text/html; charset=UTF-8",
                        "utf-8",
                        null
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorScheme.primary,
                    modifier = Modifier.size(32.dp),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorScheme.primary,
            modifier = Modifier.size(40.dp),
            strokeWidth = 3.dp
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.dr_icon_info),
                contentDescription = null,
                tint = colorScheme.error,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            if (canRetry) {
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(onClick = onRetry) {
                    Text(
                        text = stringResource(R.string.strLabelRetry),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun NoInternetContent(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.dr_icon_info),
                contentDescription = null,
                tint = colorScheme.onSurfaceVariant,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.strMsgNoInternet),
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.strMsgNoInternetLong),
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(onClick = onRetry) {
                Text(
                    text = stringResource(R.string.strLabelRetry),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun InvalidParamsContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.dr_icon_info),
                contentDescription = null,
                tint = colorScheme.error,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Invalid params",
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}
