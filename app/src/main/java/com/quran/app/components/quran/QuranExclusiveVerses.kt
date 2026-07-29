package com.quran.app.components.quran

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.quran.app.compose.utils.appPlatformLocale
import com.quran.app.utils.quran.parser.ExclusiveVersesParser

object QuranExclusiveVerses {
    suspend fun get(context: Context, dataset: ExclusiveVersesDataset): List<ExclusiveVerse> =
        ExclusiveVersesParser.parseFromAssets(context, dataset.assetKey)

    @Composable
    fun observe(dataset: ExclusiveVersesDataset, range: IntRange? = null): List<ExclusiveVerse>? {
        val context = LocalContext.current
        val configuration = LocalConfiguration.current

        val verses by produceState<List<ExclusiveVerse>?>(
            null,
            context,
            configuration,
            appPlatformLocale(),
        ) {
            val v = get(context, dataset)

            if (range == null) {
                value = v
            } else {
                value = v.subList(range.first, range.last)
            }
        }

        return verses
    }
}
