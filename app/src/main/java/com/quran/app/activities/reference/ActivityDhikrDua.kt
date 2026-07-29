package com.quran.app.activities.reference

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.dhikr.DhikrDuaScreen
import com.quran.app.compose.theme.QuranAppTheme

class ActivityDhikrDua : BaseActivity() {
    override fun getLayoutResource() = 0

    override fun shouldInflateAsynchronously() = false

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                DhikrDuaScreen()
            }
        }
    }
}
