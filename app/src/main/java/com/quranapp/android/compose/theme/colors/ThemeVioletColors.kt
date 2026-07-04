package com.quranapp.android.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeVioletColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF6366F1), // Indigo/Violet
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFE0E7FF),
            onPrimaryContainer = Color(0xFF312E81),
            secondary = Color(0xFF8B5CF6),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFEDE9FE),
            onSecondaryContainer = Color(0xFF4C1D95),
            tertiary = Color(0xFF0EA5E9),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFE0F2FE),
            onTertiaryContainer = Color(0xFF075985),
            error = Color(0xFFEF4444),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFEE2E2),
            onErrorContainer = Color(0xFF991B1B),
            background = Color(0xFFF5F7FF),
            onBackground = Color(0xFF111422),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF1D2133),
            surfaceVariant = Color(0xFFEBEDF8),
            onSurfaceVariant = Color(0xFF4C526E),
            inverseOnSurface = Color(0xFFF5F7FF),
            inverseSurface = Color(0xFF1D2133),
            inversePrimary = Color(0xFF818CF8),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF5F7FF),
            surfaceContainer = Color(0xFFFFFFFF),
            surfaceContainerHigh = Color(0xFFEBEDF8),
            surfaceContainerHighest = Color(0xFFDFE2F2),
            outline = Color(0xFFC4C8E0),
            outlineVariant = Color(0xFFDFE2F2),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFF818CF8),
            onPrimary = Color(0xFF1E1B4B),
            primaryContainer = Color(0xFF312E81),
            onPrimaryContainer = Color(0xFFE0E7FF),
            secondary = Color(0xFFA78BFA),
            onSecondary = Color(0xFF2E1065),
            secondaryContainer = Color(0xFF4C1D95),
            onSecondaryContainer = Color(0xFFEDE9FE),
            tertiary = Color(0xFF38BDF8),
            onTertiary = Color(0xFF0C4A6E),
            tertiaryContainer = Color(0xFF0369A1),
            onTertiaryContainer = Color(0xFFBAE6FD),
            error = Color(0xFFF87171),
            onError = Color(0xFF7F1D1D),
            errorContainer = Color(0xFF991B1B),
            onErrorContainer = Color(0xFFFECACA),
            background = Color(0xFF0C0F1A),
            onBackground = Color(0xFFF0F2FA),
            surface = Color(0xFF121626),
            onSurface = Color(0xFFE1E4F2),
            surfaceVariant = Color(0xFF1C223C),
            onSurfaceVariant = Color(0xFF9FA6CA),
            inverseOnSurface = Color(0xFF0C0F1A),
            inverseSurface = Color(0xFFE1E4F2),
            inversePrimary = Color(0xFF6366F1),
            surfaceContainerLowest = Color(0xFF0C0F1A),
            surfaceContainerLow = Color(0xFF0E121E),
            surfaceContainer = Color(0xFF121626),
            surfaceContainerHigh = Color(0xFF1C223C),
            surfaceContainerHighest = Color(0xFF2B3357),
            outline = Color(0xFF636B91),
            outlineVariant = Color(0xFF2B3357),
        )
    }
}