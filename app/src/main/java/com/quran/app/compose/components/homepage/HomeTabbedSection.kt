package com.quran.app.compose.components.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.quran.app.R
import com.quran.app.compose.components.HomePremiumBannerContainer
import com.quran.app.compose.components.VerseOfTheDay
import com.quran.app.compose.components.VotdContent
import com.quran.app.compose.components.reader.ReaderProvider
import com.quran.app.compose.theme.alpha
import com.quran.app.utils.recommended.Recommendation
import com.quran.app.utils.recommended.Recommended

@Composable
fun HomeTabbedSection() {
    val context = LocalContext.current
    var recommendations by remember { mutableStateOf(emptyList<Recommendation>()) }
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        recommendations = Recommended.getRecommendations(context)
    }

    if (recommendations.isEmpty()) {
        VerseOfTheDay()
        return
    }

    HomePremiumBannerContainer {
        Column {
            SecondaryTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                divider = {}) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = {
                        Text(
                            stringResource(R.string.labelRecommended), style = typography.labelLarge
                        )
                    },
                    selectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    unselectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Text(
                            stringResource(R.string.strTitleVOTD), style = typography.labelLarge
                        )
                    },
                    selectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    unselectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            HorizontalDivider(
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
            )

            androidx.compose.animation.Crossfade(
                targetState = selectedTabIndex,
                label = "HomeTabbedSectionCrossfade"
            ) { index ->
                if (index == 0) {
                    HomeSectionRecommended(recommendations)
                } else {
                    ReaderProvider {
                        VotdContent(header = {})
                    }
                }
            }
        }
    }
}
