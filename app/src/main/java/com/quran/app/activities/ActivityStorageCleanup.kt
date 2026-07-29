package com.quran.app.activities

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.storageCleanup.StorageCleanupScreen
import com.quran.app.compose.theme.QuranAppTheme

class ActivityStorageCleanup : BaseActivity() {

    override fun getLayoutResource() = 0

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                StorageCleanupScreen()
            }
        }
    }
}
