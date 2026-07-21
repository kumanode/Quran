package com.quran.app.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeRedColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFFB3261E),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFF9DEDC),
            onPrimaryContainer = Color(0xFF410E0B),
            secondary = Color(0xFF984061),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFD9E2),
            onSecondaryContainer = Color(0xFF3E001D),
            tertiary = Color(0xFF7D5260),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFFFD9E3),
            onTertiaryContainer = Color(0xFF31101D),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFFFF8F7),
            onBackground = Color(0xFF201A1A),
            surface = Color(0xFFFAF9F9),
            onSurface = Color(0xFF201A1A),
            surfaceVariant = Color(0xFFF4DDDB),
            onSurfaceVariant = Color(0xFF534341),
            inverseOnSurface = Color(0xFFF5F0F0),
            inverseSurface = Color(0xFF352F2E),
            inversePrimary = Color(0xFFF2B8B5),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFFAF2F1),
            surfaceContainer = Color(0xFFF6ECEB),
            surfaceContainerHigh = Color(0xFFF0E6E5),
            surfaceContainerHighest = Color(0xFFEAE0DF),
            outline = Color(0xFF857371),
            outlineVariant = Color(0xFFD8C2BF),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFF2B8B5),
            onPrimary = Color(0xFF601410),
            primaryContainer = Color(0xFF8C1D18),
            onPrimaryContainer = Color(0xFFF9DEDC),
            secondary = Color(0xFFFFB1C8),
            onSecondary = Color(0xFF5E1133),
            secondaryContainer = Color(0xFF7B2949),
            onSecondaryContainer = Color(0xFFFFD9E2),
            tertiary = Color(0xFFEFB8C8),
            onTertiary = Color(0xFF492532),
            tertiaryContainer = Color(0xFF633B48),
            onTertiaryContainer = Color(0xFFFFD9E3),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF1C0F0E),
            onBackground = Color(0xFFEAE1E0),
            surface = Color(0xFF201211),
            onSurface = Color(0xFFEAE1E0),
            surfaceVariant = Color(0xFF534341),
            onSurfaceVariant = Color(0xFFD8C2BF),
            inverseOnSurface = Color(0xFF1C0F0E),
            inverseSurface = Color(0xFFEAE1E0),
            inversePrimary = Color(0xFFB3261E),
            surfaceContainerLowest = Color(0xFF140908),
            surfaceContainerLow = Color(0xFF241615),
            surfaceContainer = Color(0xFF2C1C1B),
            surfaceContainerHigh = Color(0xFF372625),
            surfaceContainerHighest = Color(0xFF433130),
            outline = Color(0xFF9F8C8A),
            outlineVariant = Color(0xFF534341),
        )
    }
}