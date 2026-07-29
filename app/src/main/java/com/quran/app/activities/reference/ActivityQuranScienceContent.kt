package com.quran.app.activities.reference

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.quran.app.activities.base.BaseActivity
import com.quran.app.components.quran.QuranScienceItem
import com.quran.app.compose.screens.science.ScienceContentScreen
import com.quran.app.compose.theme.QuranAppTheme
import com.quran.app.utils.extensions.serializableExtra
import kotlinx.coroutines.flow.MutableStateFlow

class ActivityQuranScienceContent : BaseActivity() {
    private val intentFlow = MutableStateFlow<Intent?>(null)

    override fun getLayoutResource() = 0

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        intentFlow.value = intent

        setContent {
            val currentIntent by intentFlow.collectAsState()
            val item = currentIntent?.serializableExtra<QuranScienceItem>("item")

            QuranAppTheme {
                ScienceContentScreen(item = item)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        intentFlow.value = intent
    }
}

