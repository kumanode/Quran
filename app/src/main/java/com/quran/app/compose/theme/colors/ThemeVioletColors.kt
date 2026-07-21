package com.quran.app.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeVioletColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF70529A),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFF1DBFF),
            onPrimaryContainer = Color(0xFF2A0B4F),
            secondary = Color(0xFFA60067),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFD8EC),
            onSecondaryContainer = Color(0xFF3B0022),
            tertiary = Color(0xFF006684),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFBCE9FF),
            onTertiaryContainer = Color(0xFF001F2A),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFFBF8FF),
            onBackground = Color(0xFF1C1A22),
            surface = Color(0xFFFAF8FD),
            onSurface = Color(0xFF1C1A22),
            surfaceVariant = Color(0xFFE7E1EB),
            onSurfaceVariant = Color(0xFF49454E),
            inverseOnSurface = Color(0xFFF4EFF4),
            inverseSurface = Color(0xFF313033),
            inversePrimary = Color(0xFFDFBFFF),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF4F0F7),
            surfaceContainer = Color(0xFFEEEAF2),
            surfaceContainerHigh = Color(0xFFE8E4EC),
            surfaceContainerHighest = Color(0xFFE2DEE6),
            outline = Color(0xFF7A757F),
            outlineVariant = Color(0xFFCAC4D0),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFDFBFFF),
            onPrimary = Color(0xFF402268),
            primaryContainer = Color(0xFF583A80),
            onPrimaryContainer = Color(0xFFF1DBFF),
            secondary = Color(0xFFFFB0D9),
            onSecondary = Color(0xFF60003B),
            secondaryContainer = Color(0xFF880053),
            onSecondaryContainer = Color(0xFFFFD8EC),
            tertiary = Color(0xFF67D3FF),
            onTertiary = Color(0xFF003547),
            tertiaryContainer = Color(0xFF004D65),
            onTertiaryContainer = Color(0xFFBCE9FF),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF16101D),
            onBackground = Color(0xFFE6E1E9),
            surface = Color(0xFF1E1725),
            onSurface = Color(0xFFE6E1E9),
            surfaceVariant = Color(0xFF49454E),
            onSurfaceVariant = Color(0xFFCAC4D0),
            inverseOnSurface = Color(0xFF16101D),
            inverseSurface = Color(0xFFE6E1E9),
            inversePrimary = Color(0xFF70529A),
            surfaceContainerLowest = Color(0xFF100A15),
            surfaceContainerLow = Color(0xFF251D2C),
            surfaceContainer = Color(0xFF2D2434),
            surfaceContainerHigh = Color(0xFF382E3F),
            surfaceContainerHighest = Color(0xFF43394B),
            outline = Color(0xFF948F99),
            outlineVariant = Color(0xFF49454E),
        )
    }
}