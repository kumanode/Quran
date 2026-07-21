package com.quran.app.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeBlueColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF00629E),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFD0E4FF),
            onPrimaryContainer = Color(0xFF001D35),
            secondary = Color(0xFF006B5B),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFA3F4E0),
            onSecondaryContainer = Color(0xFF00201A),
            tertiary = Color(0xFF6750A4),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFEADDFF),
            onTertiaryContainer = Color(0xFF21005D),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFF8FAFC),
            onBackground = Color(0xFF1A1C1E),
            surface = Color(0xFFFAF9FD),
            onSurface = Color(0xFF1A1C1E),
            surfaceVariant = Color(0xFFDFE2EB),
            onSurfaceVariant = Color(0xFF43474E),
            inverseOnSurface = Color(0xFFF1F0F4),
            inverseSurface = Color(0xFF2F3033),
            inversePrimary = Color(0xFF9BCAFF),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF1F4F9),
            surfaceContainer = Color(0xFFEBF0F6),
            surfaceContainerHigh = Color(0xFFE5EAF1),
            surfaceContainerHighest = Color(0xFFDFE4EB),
            outline = Color(0xFF73777F),
            outlineVariant = Color(0xFFC3C7D0),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFF9BCAFF),
            onPrimary = Color(0xFF003257),
            primaryContainer = Color(0xFF00497A),
            onPrimaryContainer = Color(0xFFD0E4FF),
            secondary = Color(0xFF85D8C4),
            onSecondary = Color(0xFF00372E),
            secondaryContainer = Color(0xFF005044),
            onSecondaryContainer = Color(0xFFA3F4E0),
            tertiary = Color(0xFFD0BCFF),
            onTertiary = Color(0xFF381E72),
            tertiaryContainer = Color(0xFF4F378B),
            onTertiaryContainer = Color(0xFFEADDFF),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF0E151A),
            onBackground = Color(0xFFE2E2E6),
            surface = Color(0xFF11171E),
            onSurface = Color(0xFFE2E2E6),
            surfaceVariant = Color(0xFF43474E),
            onSurfaceVariant = Color(0xFFC3C7D0),
            inverseOnSurface = Color(0xFF0E151A),
            inverseSurface = Color(0xFFE2E2E6),
            inversePrimary = Color(0xFF00629E),
            surfaceContainerLowest = Color(0xFF0A0F13),
            surfaceContainerLow = Color(0xFF151D24),
            surfaceContainer = Color(0xFF1B222A),
            surfaceContainerHigh = Color(0xFF252D36),
            surfaceContainerHighest = Color(0xFF303943),
            outline = Color(0xFF8D9199),
            outlineVariant = Color(0xFF43474E),
        )
    }
}