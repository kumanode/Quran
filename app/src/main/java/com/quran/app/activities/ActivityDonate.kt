package com.quran.app.activities

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.DonateScreen
import com.quran.app.compose.theme.QuranAppTheme

class ActivityDonate : BaseActivity() {
    override fun shouldInflateAsynchronously() = false

    override fun getLayoutResource() = 0

    private lateinit var activityResultSender: com.solana.mobilewalletadapter.clientlib.ActivityResultSender

    private lateinit var viewModel: com.quran.app.viewModels.DonateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultSender = com.solana.mobilewalletadapter.clientlib.ActivityResultSender(this)
        viewModel = androidx.lifecycle.ViewModelProvider(this)[com.quran.app.viewModels.DonateViewModel::class.java]
    }

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                DonateScreen(activityResultSender, viewModel)
            }
        }
    }
}
