package com.quran.app

import android.app.Application
import android.content.Context
import android.os.Build
import android.webkit.WebView
import androidx.appcompat.app.AppCompatDelegate
import com.quran.app.api.RetrofitInstance
import com.quran.app.compose.utils.ThemeUtils
import com.quran.app.compose.utils.preferences.DataStoreManager
import com.quran.app.compose.utils.preferences.ReaderPreferences
import com.quran.app.compose.utils.refreshAppLocale
import com.quran.app.db.DatabaseProvider
import com.quran.app.db.bookmark.UserDataMigrationManager
import com.quran.app.search.SearchIndexScheduler
import com.quran.app.utils.app.DownloadSourceUtils
import com.quran.app.utils.app.NotificationUtils
import com.quran.app.utils.exceptions.CustomExceptionHandler
import com.quran.app.utils.mediaplayer.RecitationModelManager
import com.quran.app.utils.mediaplayer.WbwAudioRepository
import com.quran.app.utils.univ.FileUtils
import com.quran.app.viewModels.ReaderIndexViewModel
import com.quran.app.views.player.startRecitationPlayerWidgetObserver
import com.quran.app.views.reader.startVotdWidgetPreferenceObserver

class QuranApp : Application() {
    override fun attachBaseContext(base: Context) {
        initBeforeBaseAttach(base)
        super.attachBaseContext(base)
    }

    private fun initBeforeBaseAttach(base: Context) {
        FileUtils.appFilesDir = base.filesDir
    }

    private fun updateTheme() {
        AppCompatDelegate.setDefaultNightMode(ThemeUtils.resolveThemeModeForDelegate())
    }

    override fun onCreate() {
        super.onCreate()
        DataStoreManager.init(this)
        RetrofitInstance.initCache(this)
        refreshAppLocale(applicationContext)
        DownloadSourceUtils.resetDownloadSourceBaseUrl()
        NotificationUtils.createNotificationChannels(this)
        updateTheme()
        startVotdWidgetPreferenceObserver(this)
        startRecitationPlayerWidgetObserver(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val process = getProcessName()
            if (packageName != process) WebView.setDataDirectorySuffix(process)
        }

        // Handler for uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler(this))

        // Migrations
        ReaderPreferences.migrateFromLegacyIfNeeded(this)
        ReaderPreferences.repairStoredPreferencesIfNeeded(applicationContext)
        RecitationModelManager.get(this).migrateLegacyData()
        WbwAudioRepository.migrateLegacyData(applicationContext)
        ReaderIndexViewModel.migrateFavourites(this)
        UserDataMigrationManager(this).migrate()

        SearchIndexScheduler.scheduleTranslationSearchIndexIfNeeded(applicationContext)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE && level == TRIM_MEMORY_COMPLETE) {
            DatabaseProvider.closeAll()
        }
    }
}
