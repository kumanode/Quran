package com.quranapp.android.compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.quranapp.android.compose.components.MainAppBar
import com.quranapp.android.compose.components.MainBottomNavigationBar
import com.quranapp.android.compose.components.mainBottomNavigationOuterHeight
import com.quranapp.android.compose.components.player.RecitationPlayerSheet

@Composable
fun MainScreen() {
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            MainAppBar()
            HomeScreen(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = mainBottomNavigationOuterHeight()),
            )
        }

        // Bottom navigation bar overlay at bottom
        Box(
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.BottomCenter)
        ) {
            MainBottomNavigationBar()
        }

        RecitationPlayerSheet(
            collapsedBottomInset = mainBottomNavigationOuterHeight(),
        )
    }
}
