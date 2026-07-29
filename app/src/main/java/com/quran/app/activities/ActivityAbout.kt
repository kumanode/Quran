package com.quran.app.activities

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.AboutScreen
import com.quran.app.compose.theme.QuranAppTheme

class ActivityAbout : BaseActivity() {
    override fun shouldInflateAsynchronously() = false

    override fun getLayoutResource() = 0

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                AboutScreen()
            }
        }
    }
}
