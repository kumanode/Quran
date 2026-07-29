package com.quran.app.activities.reference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.screens.reference.ExclusiveVersesListScreen
import com.quran.app.compose.screens.reference.ExclusiveVersesScreenKind
import com.quran.app.compose.theme.QuranAppTheme
import com.quran.app.utils.univ.Keys

class ActivityExclusiveVerses : BaseActivity() {
    override fun getLayoutResource() = 0

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        val kind = intent.getStringExtra(Keys.KEY_EXCLUSIVE_VERSES_KIND).let { name ->
            ExclusiveVersesScreenKind.entries.find { it.name == name }
                ?: ExclusiveVersesScreenKind.Dua
        }

        setContent {
            QuranAppTheme {
                ExclusiveVersesListScreen(kind = kind)
            }
        }
    }

    companion object {
        fun intent(context: Context, kind: ExclusiveVersesScreenKind): Intent =
            Intent(context, ActivityExclusiveVerses::class.java).apply {
                putExtra(Keys.KEY_EXCLUSIVE_VERSES_KIND, kind.name)
            }
    }
}
