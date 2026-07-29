package com.quran.app.activities

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.onboarding.OnboardingScreen
import com.quran.app.compose.theme.QuranAppTheme
import com.quran.app.utils.sharedPrefs.SPAppActions.setRequireOnboarding

class ActivityOnboarding : BaseActivity() {

    override fun getLayoutResource() = 0

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        setContent {
            QuranAppTheme {
                OnboardingScreen(onComplete = ::takeOff)
            }
        }
    }

    private fun takeOff() {
        setRequireOnboarding(this, false)
        launchMainActivity()
        finish()
    }
}
