package com.quran.app.activities

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.reader.ReaderIndexScreen
import com.quran.app.compose.theme.QuranAppTheme

class ActivityReaderIndexPage : BaseActivity() {

    override fun getLayoutResource(): Int {
        return 0
    }

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContentView(ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                QuranAppTheme {
                    ReaderIndexScreen()
                }
            }
        })
    }

}
