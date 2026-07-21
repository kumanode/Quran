package com.quran.app.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeMonoColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF5D5E61),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFE2E2E6),
            onPrimaryContainer = Color(0xFF1A1C1E),
            secondary = Color(0xFF5E5E62),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFE2E2E6),
            onSecondaryContainer = Color(0xFF1A1C1E),
            tertiary = Color(0xFF5F5E61),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFE3E2E6),
            onTertiaryContainer = Color(0xFF1A1C1E),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFF9F9FB),
            onBackground = Color(0xFF1A1C1E),
            surface = Color(0xFFFCFCFF),
            onSurface = Color(0xFF1A1C1E),
            surfaceVariant = Color(0xFFE1E2E5),
            onSurfaceVariant = Color(0xFF44474E),
            inverseOnSurface = Color(0xFFF2F0F4),
            inverseSurface = Color(0xFF2F3033),
            inversePrimary = Color(0xFFC6C6CA),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF3F3F6),
            surfaceContainer = Color(0xFFEDEDF0),
            surfaceContainerHigh = Color(0xFFE7E7EA),
            surfaceContainerHighest = Color(0xFFE1E2E5),
            outline = Color(0xFF75777A),
            outlineVariant = Color(0xFFC5C6C9),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFC6C6CA),
            onPrimary = Color(0xFF2F3033),
            primaryContainer = Color(0xFF46474A),
            onPrimaryContainer = Color(0xFFE2E2E6),
            secondary = Color(0xFFC6C6CA),
            onSecondary = Color(0xFF2F3033),
            secondaryContainer = Color(0xFF46474A),
            onSecondaryContainer = Color(0xFFE2E2E6),
            tertiary = Color(0xFFC7C6CA),
            onTertiary = Color(0xFF2F3033),
            tertiaryContainer = Color(0xFF46474A),
            onTertiaryContainer = Color(0xFFE3E2E6),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF121214),
            onBackground = Color(0xFFE3E2E6),
            surface = Color(0xFF1B1B1E),
            onSurface = Color(0xFFE3E2E6),
            surfaceVariant = Color(0xFF44474E),
            onSurfaceVariant = Color(0xFFC5C6C9),
            inverseOnSurface = Color(0xFF121214),
            inverseSurface = Color(0xFFE3E2E6),
            inversePrimary = Color(0xFF5D5E61),
            surfaceContainerLowest = Color(0xFF0C0C0E),
            surfaceContainerLow = Color(0xFF1E1E20),
            surfaceContainer = Color(0xFF262629),
            surfaceContainerHigh = Color(0xFF313134),
            surfaceContainerHighest = Color(0xFF3C3C3F),
            outline = Color(0xFF8F9093),
            outlineVariant = Color(0xFF44474E),
        )
    }
}