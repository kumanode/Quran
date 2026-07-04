package com.quranapp.android.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemePurpleColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF8B5CF6), // Purple
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFEDE9FE),
            onPrimaryContainer = Color(0xFF4C1D95),
            secondary = Color(0xFFEC4899),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFCE7F3),
            onSecondaryContainer = Color(0xFF831843),
            tertiary = Color(0xFF3B82F6),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFDBEAFE),
            onTertiaryContainer = Color(0xFF1E3A8A),
            error = Color(0xFFEF4444),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFEE2E2),
            onErrorContainer = Color(0xFF991B1B),
            background = Color(0xFFFAF5FF),
            onBackground = Color(0xFF1B1425),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF282033),
            surfaceVariant = Color(0xFFF3E8FF),
            onSurfaceVariant = Color(0xFF5B496E),
            inverseOnSurface = Color(0xFFFAF5FF),
            inverseSurface = Color(0xFF282033),
            inversePrimary = Color(0xFFA78BFA),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFFAF5FF),
            surfaceContainer = Color(0xFFFFFFFF),
            surfaceContainerHigh = Color(0xFFF3E8FF),
            surfaceContainerHighest = Color(0xFFE9D5FF),
            outline = Color(0xFFD8C4EA),
            outlineVariant = Color(0xFFE9D5FF),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFA78BFA),
            onPrimary = Color(0xFF2E1065),
            primaryContainer = Color(0xFF4C1D95),
            onPrimaryContainer = Color(0xFFEDE9FE),
            secondary = Color(0xFFF472B6),
            onSecondary = Color(0xFF500724),
            secondaryContainer = Color(0xFF831843),
            onSecondaryContainer = Color(0xFFFCE7F3),
            tertiary = Color(0xFF60A5FA),
            onTertiary = Color(0xFF172554),
            tertiaryContainer = Color(0xFF1E3A8A),
            onTertiaryContainer = Color(0xFFDBEAFE),
            error = Color(0xFFF87171),
            onError = Color(0xFF7F1D1D),
            errorContainer = Color(0xFF991B1B),
            onErrorContainer = Color(0xFFFECACA),
            background = Color(0xFF110D17),
            onBackground = Color(0xFFF5EBF9),
            surface = Color(0xFF1A1324),
            onSurface = Color(0xFFE9D5FF),
            surfaceVariant = Color(0xFF2A1C3C),
            onSurfaceVariant = Color(0xFFBAA1D4),
            inverseOnSurface = Color(0xFF110D17),
            inverseSurface = Color(0xFFE9D5FF),
            inversePrimary = Color(0xFF8B5CF6),
            surfaceContainerLowest = Color(0xFF110D17),
            surfaceContainerLow = Color(0xFF150F1D),
            surfaceContainer = Color(0xFF1A1324),
            surfaceContainerHigh = Color(0xFF2A1C3C),
            surfaceContainerHighest = Color(0xFF3F2A59),
            outline = Color(0xFF755C8F),
            outlineVariant = Color(0xFF3F2A59),
        )
    }
}