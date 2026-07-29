package com.quran.app.activities

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.settings.AppLogsScreen
import com.quran.app.compose.theme.QuranAppTheme

class ActivityAppLogs : BaseActivity() {
    override fun getLayoutResource(): Int {
        return 0
    }

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                AppLogsScreen()
            }
        }
    }

}
