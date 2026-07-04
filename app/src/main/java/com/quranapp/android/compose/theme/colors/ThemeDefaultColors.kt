package com.quranapp.android.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeDefaultColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF0D3B2E), // Deep emerald green
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFF1E6752),
            onPrimaryContainer = Color(0xFFFFFFFF),
            secondary = Color(0xFFD4AF7A), // Soft gold
            onSecondary = Color(0xFF3B270C),
            secondaryContainer = Color(0xFFF2D6AB),
            onSecondaryContainer = Color(0xFF4C3410),
            tertiary = Color(0xFF1E3A5F),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFD1E4FF),
            onTertiaryContainer = Color(0xFF001D36),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFF8F9FA), 
            onBackground = Color(0xFF0B1220), 
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF0B1220),
            surfaceVariant = Color(0xFFDFE4E0),
            onSurfaceVariant = Color(0xFF404944),
            inverseOnSurface = Color(0xFFF0F1F0),
            inverseSurface = Color(0xFF2E3130),
            inversePrimary = Color(0xFF5DD5A7),
            surfaceContainer = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF8F9FA),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerHigh = Color(0xFFF0F2F1),
            surfaceContainerHighest = Color(0xFFE6EAE8),
            outline = Color(0xFF707973),
            outlineVariant = Color(0xFFC0C9C2),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFF5DD5A7), // Light green
            onPrimary = Color(0xFF042115),
            primaryContainer = Color(0xFF07241B),
            onPrimaryContainer = Color(0xFF5DD5A7),
            secondary = Color(0xFFD4AF7A), // Soft gold
            onSecondary = Color(0xFF3B270C),
            secondaryContainer = Color(0xFF553D16),
            onSecondaryContainer = Color(0xFFF2D6AB),
            tertiary = Color(0xFF4A729C),
            onTertiary = Color(0xFF0B1220),
            tertiaryContainer = Color(0xFF1E3A5F),
            onTertiaryContainer = Color(0xFFD1E4FF),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF0B1220), // Midnight navy
            onBackground = Color(0xFFE0E6ED),
            surface = Color(0xFF111A2E), 
            onSurface = Color(0xFFE0E6ED),
            surfaceVariant = Color(0xFF1F2B42),
            onSurfaceVariant = Color(0xFFBFC7D6),
            inverseOnSurface = Color(0xFF0B1220),
            inverseSurface = Color(0xFFE0E6ED),
            inversePrimary = Color(0xFF5DD5A7),
            surfaceContainerLow = Color(0xFF0E1627),
            surfaceContainer = Color(0xFF111A2E),
            surfaceContainerLowest = Color(0xFF09101C),
            surfaceContainerHigh = Color(0xFF1B2438),
            surfaceContainerHighest = Color(0xFF26334A),
            outline = Color(0xFF89938D),
            outlineVariant = Color(0xFF3F4944),
        )
    }
}