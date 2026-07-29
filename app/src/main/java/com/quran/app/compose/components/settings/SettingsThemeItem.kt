package com.quran.app.compose.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quran.app.compose.screens.settings.ThemeItem
import com.quran.app.compose.theme.alpha

@Composable
fun SettingsThemeItem(
    themeItem: ThemeItem,
    isDarkTheme: Boolean,
    currentThemeColor: String,
    onChange: (String) -> Unit
) {
    val colorScheme =
        if (isDarkTheme) themeItem.color.darkColors() else themeItem.color.lightColors()
    val isSelected = currentThemeColor == themeItem.id

    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = {
                onChange(themeItem.id)
            })
            .border(
                2.dp,
                colorScheme.primary.alpha(if (isSelected) 1f else 0.2f),
                MaterialTheme.shapes.medium
            )
            .background(if (isSelected) colorScheme.primary.alpha(0.2f) else colorScheme.surfaceContainerHigh)
            .padding(PaddingValues(15.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = themeItem.title),
            color = if (isSelected) colorScheme.primary else colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}
