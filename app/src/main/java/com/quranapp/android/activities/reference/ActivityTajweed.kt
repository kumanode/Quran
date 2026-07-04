package com.quranapp.android.activities.reference

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quranapp.android.activities.base.BaseActivity
import com.quranapp.android.compose.screens.tajweed.TajweedScreen
import com.quranapp.android.compose.theme.QuranAppTheme

class ActivityTajweed : BaseActivity() {
    override fun getLayoutResource() = 0

    override fun shouldInflateAsynchronously() = false

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                TajweedScreen()
            }
        }
    }
}
