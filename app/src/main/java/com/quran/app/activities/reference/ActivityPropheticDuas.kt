package com.quran.app.activities.reference

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.reference.PropheticDuasScreen
import com.quran.app.compose.theme.QuranAppTheme
import com.quran.app.utils.univ.Keys

class ActivityPropheticDuas : BaseActivity() {
    override fun getLayoutResource() = 0


    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        val title = intent.getStringExtra(Keys.KEY_EXTRA_TITLE)

        setContent {
            QuranAppTheme {
                PropheticDuasScreen(title = title)
            }
        }
    }
}
