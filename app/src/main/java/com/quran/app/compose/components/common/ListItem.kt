package com.quran.app.compose.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    title: Int,
    subtitle: Int? = null,
    subtitleStr: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    val isLiquidGlass = com.quran.app.compose.utils.ThemeUtils.LocalLiquidGlass()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .then(if (isLiquidGlass) Modifier else Modifier.shadow(2.dp))
            .background(colorScheme.surface)
            .then(if (isLiquidGlass) Modifier.border(1.dp, colorScheme.outlineVariant.copy(alpha = 0.5f), MaterialTheme.shapes.medium) else Modifier),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (leading != null) leading()

            ListItemContent(
                title = title,
                subtitle = subtitle,
                subtitleStr = subtitleStr,
                modifier = Modifier.weight(1f)
            )

            if (trailing != null) {
                trailing()
            }
        }
    }
}

@Composable
fun ListItemContent(
    title: Int? = null,
    titleStr: String? = null,
    subtitle: Int? = null,
    subtitleStr: String? = null,
    modifier: Modifier
) {
    val titleText = titleStr ?: if (title != null) stringResource(title) else null
    val subtitleText = subtitleStr.takeIf { !it.isNullOrEmpty() }
        ?: if (subtitle != null) stringResource(subtitle) else null

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        if (titleText != null) {
            Text(
                text = titleText,
                style = MaterialTheme.typography.labelLarge.copy(
                    lineHeightStyle = LineHeightStyle.Default.copy(
                        mode = LineHeightStyle.Mode.Tight,
                        alignment = LineHeightStyle.Alignment.Center,
                    )
                ),
                color = colorScheme.onSurface
            )
        }

        if (subtitleText != null) {
            Text(
                text = subtitleText,
                style = MaterialTheme.typography.labelMedium.copy(
                    lineHeightStyle = LineHeightStyle.Default.copy(
                        mode = LineHeightStyle.Mode.Tight,
                        alignment = LineHeightStyle.Alignment.Center,
                    )
                ),
                color = colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .alpha(0.75f),
            )
        }
    }
}
