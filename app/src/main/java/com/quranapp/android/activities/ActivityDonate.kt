package com.quranapp.android.activities

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quranapp.android.activities.base.BaseActivity
import com.quranapp.android.compose.screens.DonateScreen
import com.quranapp.android.compose.theme.QuranAppTheme

class ActivityDonate : BaseActivity() {
    override fun shouldInflateAsynchronously() = false

    override fun getLayoutResource() = 0

    private lateinit var activityResultSender: com.solana.mobilewalletadapter.clientlib.ActivityResultSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultSender = com.solana.mobilewalletadapter.clientlib.ActivityResultSender(this)
    }

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                DonateScreen(activityResultSender)
            }
        }
    }
}
