package com.quran.app.activities.reference

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.R
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.reference.ProphetsScreen
import com.quran.app.compose.theme.QuranAppTheme

class ActivityProphets : BaseActivity() {
    override fun getLayoutResource() = 0

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                ProphetsScreen()
            }
        }
    }
}
