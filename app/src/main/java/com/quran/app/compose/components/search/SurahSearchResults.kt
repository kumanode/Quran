package com.quran.app.compose.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.quran.app.R
import com.quran.app.compose.components.common.Loader
import com.quran.app.compose.components.reader.navigator.ChapterCard
import com.quran.app.db.relations.SurahWithLocalizations
import com.quran.app.utils.reader.factory.ReaderFactory
import com.quran.app.viewModels.QuranSearchViewModel

@Composable
fun SurahSearchResults(
    viewModel: QuranSearchViewModel,
    surahResults: List<SurahWithLocalizations>?,
) {
    if (surahResults == null) {
        return Loader(true)
    }

    if (surahResults.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.noResults),
                style = typography.labelLarge,
            )
        }
        return
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(surahResults) {
            ChapterCard(
                surah = it,
                onClick = {
                    viewModel.commitOnIntentionalOutcome()
                    ReaderFactory.startChapter(context, it.surah.surahNo)
                },
            )
        }
    }
}