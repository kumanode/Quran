package com.quran.app.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemePurpleColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF6750A4),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFEADDFF),
            onPrimaryContainer = Color(0xFF21005D),
            secondary = Color(0xFF7F525D),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFD9E1),
            onSecondaryContainer = Color(0xFF32101A),
            tertiary = Color(0xFF7D5260),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFFFD9E3),
            onTertiaryContainer = Color(0xFF31101D),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFFDF8FD),
            onBackground = Color(0xFF1D1B20),
            surface = Color(0xFFFAF9F9),
            onSurface = Color(0xFF1D1B20),
            surfaceVariant = Color(0xFFE7E1E1),
            onSurfaceVariant = Color(0xFF49454E),
            inverseOnSurface = Color(0xFFF4EFF4),
            inverseSurface = Color(0xFF313033),
            inversePrimary = Color(0xFFD0BCFF),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF6F2F7),
            surfaceContainer = Color(0xFFF0EBF2),
            surfaceContainerHigh = Color(0xFFEAE5EC),
            surfaceContainerHighest = Color(0xFFE4DFE6),
            outline = Color(0xFF79747E),
            outlineVariant = Color(0xFFCAC4D0),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFD0BCFF),
            onPrimary = Color(0xFF381E72),
            primaryContainer = Color(0xFF4F378B),
            onPrimaryContainer = Color(0xFFEADDFF),
            secondary = Color(0xFFF1B7C5),
            onSecondary = Color(0xFF4B2530),
            secondaryContainer = Color(0xFF643B46),
            onSecondaryContainer = Color(0xFFFFD9E1),
            tertiary = Color(0xFFEFB8C8),
            onTertiary = Color(0xFF492532),
            tertiaryContainer = Color(0xFF633B48),
            onTertiaryContainer = Color(0xFFFFD9E3),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF150F1D),
            onBackground = Color(0xFFE6E1E5),
            surface = Color(0xFF1D1726),
            onSurface = Color(0xFFE6E1E5),
            surfaceVariant = Color(0xFF49454E),
            onSurfaceVariant = Color(0xFFCAC4D0),
            inverseOnSurface = Color(0xFF150F1D),
            inverseSurface = Color(0xFFE6E1E5),
            inversePrimary = Color(0xFF6750A4),
            surfaceContainerLowest = Color(0xFF0F0915),
            surfaceContainerLow = Color(0xFF241C2D),
            surfaceContainer = Color(0xFF2B2235),
            surfaceContainerHigh = Color(0xFF362C40),
            surfaceContainerHighest = Color(0xFF42374C),
            outline = Color(0xFF938F99),
            outlineVariant = Color(0xFF49454E),
        )
    }
}