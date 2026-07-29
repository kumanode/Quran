package com.quran.app.compose.components.homepage

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.quran.app.compose.theme.tightTextStyle
import com.quran.app.R
import com.quran.app.activities.ActivityReadHistory
import com.quran.app.activities.ActivityReader
import com.quran.app.compose.components.reader.ReaderMode
import com.quran.app.compose.screens.subtitleLabel
import com.quran.app.compose.screens.titleLabel
import com.quran.app.compose.theme.alpha
import com.quran.app.db.entities.user.ReadHistoryEntity
import com.quran.app.utils.reader.ReadType
import com.quran.app.utils.reader.factory.ReaderFactory
import com.quran.app.viewModels.ReadHistoryViewModel

@Composable
fun HomeSectionReadHistory() {
    val viewModel = viewModel<ReadHistoryViewModel>()
    val chapterNames by viewModel.chapterNames.collectAsStateWithLifecycle()
    val recentHistories by viewModel.recentHistories.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        HomeSectionHeader(
            icon = R.drawable.dr_icon_history,
            title = R.string.strTitleReadHistory,
            onViewAllClick = {
                context.startActivity(Intent(context, ActivityReadHistory::class.java))
            },
        )

        when {
            recentHistories.isEmpty() -> {
                Text(
                    text = stringResource(R.string.strMsgReadShowupHere),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    fontStyle = FontStyle.Italic,
                )
            }

            else -> {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    recentHistories.take(3).forEach { history ->
                        ItemCard(
                            history = history,
                            chapterName = chapterNames.get(history.chapterNo).orEmpty(),
                            onOpen = {
                                ReaderFactory.prepareHistoryIntent(history)?.let {
                                    it.setClass(context, ActivityReader::class.java)
                                    context.startActivity(it)
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun ItemCard(
    history: ReadHistoryEntity,
    chapterName: String,
    onOpen: () -> Unit,
) {
    val readType = ReadType.fromValue(history.readType)
    val title = history.titleLabel(chapterName)
    val subtitle = history.subtitleLabel(chapterName)

    val accentColor = when (readType) {
        ReadType.Chapter -> colorScheme.primary
        ReadType.Juz -> colorScheme.tertiary
        ReadType.Hizb -> colorScheme.secondary
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        accentColor.copy(alpha = 0.3f),
                        colorScheme.outlineVariant.copy(alpha = 0.3f),
                    )
                ),
                shape = RoundedCornerShape(16.dp),
            )
            .background(colorScheme.surfaceContainerLow)
            .clickable(onClick = onOpen),
    ) {
        // Subtle left accent strip
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            accentColor,
                            accentColor.copy(alpha = 0.3f),
                        )
                    )
                )
                .align(Alignment.CenterStart),
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(
                        when (ReaderMode.fromValue(history.readerMode)) {
                            ReaderMode.Reading -> R.drawable.ic_mode_mushaf
                            ReaderMode.Translation -> R.drawable.ic_mode_translation
                            else -> R.drawable.ic_mode_verse
                        }
                    ),
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = typography.labelLarge.merge(tightTextStyle),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorScheme.onSurface,
                )

                subtitle?.let {
                    Spacer(Modifier.height(1.dp))
                    Text(
                        text = it,
                        style = typography.bodySmall.merge(tightTextStyle),
                        color = colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
