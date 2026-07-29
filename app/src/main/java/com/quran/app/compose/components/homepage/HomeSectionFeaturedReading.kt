package com.quran.app.compose.components.homepage

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quran.app.compose.theme.tightTextStyle
import com.quran.app.R
import com.quran.app.compose.theme.alpha
import com.quran.app.compose.utils.LocalAppLocale
import com.quran.app.db.DatabaseProvider
import com.quran.app.repository.QuranRepository
import com.quran.app.utils.reader.factory.ReaderFactory

private data class FeaturedQuranModel(
    val chapterNo: Int,
    val verseRange: Pair<Int, Int>,
) {
    var title: String = ""
    var subtext: String = ""
}

@Composable
fun HomeSectionFeaturedReading() {
    val featuredItems by getFeaturedQuranModels()

    if (featuredItems == null) return

    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        HomeSectionHeader(
            icon = R.drawable.dr_icon_feature,
            title = R.string.strTitleFeaturedQuran,
            iconTint = null
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(featuredItems!!, key = { it.chapterNo.toString() + it.subtext }) {
                FeaturedQuranCard(it)
            }
        }
    }
}


@Composable
private fun FeaturedQuranCard(
    model: FeaturedQuranModel
) {
    val context = LocalContext.current

    val isLiquidGlass = com.quran.app.compose.utils.ThemeUtils.LocalLiquidGlass()

    androidx.compose.material3.Card(
        onClick = { ReaderFactory.startVerseRange(context, model.chapterNo, model.verseRange) },
        modifier = Modifier
            .width(260.dp)
            .height(130.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = if (isLiquidGlass) 0.dp else 2.dp),
        border = if (isLiquidGlass) androidx.compose.foundation.BorderStroke(1.dp, androidx.compose.material3.MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)) else null
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.dr_quran_wallpaper),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            1f to Color.Black.alpha(0.85f)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = model.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ).merge(tightTextStyle),
                    color = Color.White,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = model.subtext,
                    style = MaterialTheme.typography.bodySmall.merge(tightTextStyle),
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            
            androidx.compose.material3.Icon(
                painter = painterResource(R.drawable.dr_icon_feature),
                contentDescription = null,
                tint = Color.White.alpha(0.5f),
                modifier = Modifier
                    .align(androidx.compose.ui.Alignment.TopEnd)
                    .padding(16.dp)
                    .size(18.dp)
            )
        }
    }
}

@Composable
private fun getFeaturedQuranModels(): State<List<FeaturedQuranModel>?> {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val resources = LocalResources.current
    val appLocale = LocalAppLocale.current
    val locale = appLocale.platformLocale
    appLocale.fallbackLanguageCodes().toList()

    return produceState<List<FeaturedQuranModel>?>(
        null,
        context,
        configuration,
        resources,
        appLocale,
    ) {
        val repo = DatabaseProvider.getQuranRepository(context)

        val itemsArray = resources.obtainTypedArray(R.array.arrFeaturedQuranItems)

        val chapNameFormat = resources.getString(R.string.strLabelSurah)
        val verseNoFormat = resources.getString(R.string.strLabelVerseNo)
        val versesFormat = resources.getString(R.string.strLabelVerses)
        val miniInfoFormat = resources.getString(R.string.strLabelVerseWithChapNameWithBar)
        val miniInfoChapFormat = resources.getString(R.string.strLabelFeatureQuranMiniInfo)

        val models = List(itemsArray.length()) { i ->
            val raw = itemsArray.getString(i)!!
            val (chapterNo, start, end) = parseItem(raw, repo)

            val chapterName = repo.getChapterName(chapterNo)

            FeaturedQuranModel(
                chapterNo,
                start to end,
            ).apply {
                if (start == 1 && end == repo.getChapterVerseCount(chapterNo) && !raw.contains(":")) {
                    title = String.format(locale, chapNameFormat, chapterName)
                    subtext = String.format(locale, miniInfoChapFormat, chapterNo, 1, end)
                } else if (start == end) {
                    if (chapterNo == 2 && start == 255) {
                        title = resources.getString(R.string.strAyatulKursi)
                        subtext = String.format(locale, miniInfoFormat, chapterName, 255)
                    } else {
                        title = String.format(locale, chapNameFormat, chapterName)
                        subtext = String.format(locale, verseNoFormat, start)
                    }
                } else {
                    title = String.format(locale, chapNameFormat, chapterName)
                    subtext = String.format(locale, versesFormat, start, end)
                }
            }
        }

        itemsArray.recycle()

        value = models
    }
}

private suspend fun parseItem(
    raw: String,
    repo: QuranRepository
): Triple<Int, Int, Int> {
    val colonIndex = raw.indexOf(':')

    if (colonIndex == -1) {
        val chapter = raw.toInt()
        return Triple(
            chapter,
            1,
            repo.getChapterVerseCount(chapter)
        )
    }

    val chapter = raw.substring(0, colonIndex).toInt()
    val versePart = raw.substring(colonIndex + 1)

    val dashIndex = versePart.indexOfFirst { it == '-' || it == '–' }

    return if (dashIndex == -1) {
        val verse = versePart.toInt()
        Triple(chapter, verse, verse)
    } else {
        val start = versePart.substring(0, dashIndex).toInt()
        val end = versePart.substring(dashIndex + 1).toInt()
        Triple(chapter, start, end)
    }
}
