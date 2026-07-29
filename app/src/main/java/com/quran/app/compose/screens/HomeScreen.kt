package com.quran.app.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.quran.app.compose.components.homepage.AppUpdateBanner
import com.quran.app.compose.components.homepage.HomeResourceUpdatesBanner
import com.quran.app.compose.components.homepage.HomeSectionFeaturedReading
import com.quran.app.compose.components.homepage.HomeSectionReadHistory
import com.quran.app.compose.components.homepage.HomeSectionVersesCollections
import com.quran.app.compose.components.homepage.HomeTabbedSection
import com.quran.app.compose.components.player.MINI_PLAYER_HEIGHT

private const val HOME_HISTORY_LIMIT = 10

@Composable
fun HomeScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = MINI_PLAYER_HEIGHT)
            .padding(bottom = 16.dp),
    ) {
        AppUpdateBanner()
        HomeResourceUpdatesBanner()

        HomeTabbedSection()

        HomeSectionReadHistory()
        HomeSectionFeaturedReading()

        HomeSectionVersesCollections()
    }
}