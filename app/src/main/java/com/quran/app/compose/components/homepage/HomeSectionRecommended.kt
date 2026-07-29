package com.quran.app.compose.components.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quran.app.R
import com.quran.app.components.ReferenceVerseModel
import com.quran.app.db.DatabaseProvider
import com.quran.app.utils.reader.factory.ReaderFactory
import com.quran.app.utils.recommended.Recommendation
import com.quran.app.utils.recommended.RecommendationRef

@Composable
fun HomeSectionRecommended(
    recommendations: List<Recommendation>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        recommendations.forEach { recommendation ->
            RecommendationCard(recommendation)
        }
    }
}

@Composable
private fun RecommendationCard(
    recommendation: Recommendation
) {
    val context = LocalContext.current

    val resolvedChapterName by produceState<String?>(initialValue = null) {
        val quranRepository = DatabaseProvider.getQuranRepository(context)

        val ref = recommendation.reference
        if (ref is RecommendationRef.Chapter) {
            value = quranRepository.getChapterName(ref.number)
        }
    }

    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.medium)
            .background(colors.surfaceContainerLow)
            .clickable {
                when (val ref = recommendation.reference) {
                    is RecommendationRef.Chapter -> {
                        ReaderFactory.startChapter(context, ref.number)
                    }

                    is RecommendationRef.Verses -> {
                        val ranges = ref.spec.split(',')
                        val chapters = mutableSetOf<Int>()
                        val verseSpecs = mutableSetOf<String>()

                        ranges.forEach { rangeSpec ->
                            val trimmed = rangeSpec.trim()

                            val chapterNo = trimmed.split(':')[0].toIntOrNull() ?: 0
                            chapters.add(chapterNo)

                            verseSpecs.add(trimmed)
                        }

                        ReaderFactory.startReferenceVerse(
                            context = context,
                            ReferenceVerseModel(
                                title = recommendation.title,
                                desc = recommendation.description,
                                chapters = chapters,
                                verses = verseSpecs
                            )
                        )

                        return@clickable
                    }
                }
            }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                resolvedChapterName?.let { name ->
                    Text(
                        text = stringResource(R.string.strLabelSurah, name),
                        style = MaterialTheme.typography.titleSmall,
                        color = colors.onSurface
                    )
                }

                Text(
                    text = recommendation.title,
                    style = if (resolvedChapterName != null) MaterialTheme.typography.labelMedium
                    else MaterialTheme.typography.titleSmall,
                    fontWeight = if (resolvedChapterName != null) FontWeight.Medium else FontWeight.Bold,
                    color = if (resolvedChapterName != null) colors.onSurfaceVariant else colors.onSurface
                )

                if (recommendation.description.isNotBlank()) {
                    Text(
                        text = recommendation.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }

            Icon(
                painter = painterResource(R.drawable.dr_icon_chevron_right),
                contentDescription = null,
                tint = colors.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
