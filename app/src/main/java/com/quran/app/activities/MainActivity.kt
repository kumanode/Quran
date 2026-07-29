package com.quran.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.MainScreen
import com.quran.app.compose.theme.QuranAppTheme
import com.quran.app.utils.app.AppActions.checkForCrashLogs
import com.quran.app.utils.app.AppActions.scheduleActions
import com.quran.app.utils.app.UpdateManager
import com.quran.app.utils.sharedPrefs.SPAppActions
import com.quran.app.views.reader.updateAllVotdWidgets

class MainActivity : BaseActivity() {
    private var mUpdateManager: UpdateManager? = null

    override fun getLayoutResource() = 0

    override fun onResume() {
        super.onResume()

        updateAllVotdWidgets(this)
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        if (UpdateManager.getInstance(this).check4CriticalUpdate()) {
            return
        }

        if (this.isOnboardingRequired) {
            initOnboarding()
            return
        }

        super.initCreate(savedInstanceState)
    }

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        if (this.isOnboardingRequired) {
            return
        }

        initActions()

        setContent {
            QuranAppTheme {
                MainScreen()
            }
        }
    }


    private fun initActions() {
        scheduleActions(this)
        checkForCrashLogs(this)
    }


    private val isOnboardingRequired get() = SPAppActions.getRequireOnboarding(this)

    private fun initOnboarding() {
        startActivity(Intent(this, ActivityOnboarding::class.java))
        finish()
    }
}
