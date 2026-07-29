package com.quran.app.compose.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.quran.app.R
import com.quran.app.compose.components.common.AppBar
import com.quran.app.compose.components.common.ErrorMessageCard
import com.quran.app.compose.components.common.IconButton
import com.quran.app.compose.components.common.Loader
import com.quran.app.compose.components.settings.TranslationDownloadList
import com.quran.app.compose.navigation.SettingRoutes
import com.quran.app.utils.univ.MessageUtils
import com.quran.app.viewModels.TranslationDownloadEvent
import com.quran.app.viewModels.TranslationDownloadUiEvent
import com.quran.app.viewModels.TranslationDownloadViewModel
import com.quran.app.viewModels.TranslationEvent
import com.quran.app.viewModels.TranslationViewModel

@Composable
fun TranslationDownloadScreen() {
    val context = LocalContext.current
    val resources = LocalResources.current
    val navController = LocalSettingsNavController.current
    val viewModel = viewModel<TranslationDownloadViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(navController) {
        onDispose {
            runCatching {
                val entry = navController.getBackStackEntry(SettingRoutes.TRANSLATIONS)
                val translationVm = ViewModelProvider(entry)[TranslationViewModel::class.java]
                translationVm.onEvent(TranslationEvent.RefreshQuiet)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is TranslationDownloadUiEvent.ShowMessage -> {
                    MessageUtils.popMessage(
                        context,
                        title = event.title,
                        msg = event.message,
                        resources.getString(R.string.strLabelClose),
                        null
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                stringResource(R.string.strTitleDownloadTranslations),
                searchPlaceholder = stringResource(R.string.strHintSearch),
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = {
                    viewModel.onEvent(TranslationDownloadEvent.Search(it))
                },
                actions = {
                    IconButton(
                        painterResource(R.drawable.dr_icon_refresh)
                    ) {
                        viewModel.onEvent(TranslationDownloadEvent.Refresh)
                    }
                }
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                uiState.isLoading -> Loader(true)
                uiState.error != null -> ErrorMessageCard(
                    error = uiState.error!!,
                    onRetry = { viewModel.onEvent(TranslationDownloadEvent.Refresh) }
                )

                else -> TranslationDownloadList(
                    groups = uiState.groups,
                    searchQuery = uiState.searchQuery,
                    downloadStates = uiState.downloadStates,
                    onEvent = viewModel::onEvent,
                )
            }
        }
    }
}
