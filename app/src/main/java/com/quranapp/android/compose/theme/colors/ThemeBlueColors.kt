package com.quranapp.android.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeBlueColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF3B82F6), // Blue
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFDBEAFE),
            onPrimaryContainer = Color(0xFF1E3A8A),
            secondary = Color(0xFF10B981),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFD1FAE5),
            onSecondaryContainer = Color(0xFF064E3B),
            tertiary = Color(0xFF8B5CF6),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFEDE9FE),
            onTertiaryContainer = Color(0xFF4C1D95),
            error = Color(0xFFEF4444),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFEE2E2),
            onErrorContainer = Color(0xFF991B1B),
            background = Color(0xFFF8FAFC),
            onBackground = Color(0xFF0F172A),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF1E293B),
            surfaceVariant = Color(0xFFF1F5F9),
            onSurfaceVariant = Color(0xFF475569),
            inverseOnSurface = Color(0xFFF8FAFC),
            inverseSurface = Color(0xFF1E293B),
            inversePrimary = Color(0xFF60A5FA),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF8FAFC),
            surfaceContainer = Color(0xFFFFFFFF),
            surfaceContainerHigh = Color(0xFFF1F5F9),
            surfaceContainerHighest = Color(0xFFE2E8F0),
            outline = Color(0xFFCBD5E1),
            outlineVariant = Color(0xFFE2E8F0),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFF60A5FA),
            onPrimary = Color(0xFF172554),
            primaryContainer = Color(0xFF1E3A8A),
            onPrimaryContainer = Color(0xFFDBEAFE),
            secondary = Color(0xFF34D399),
            onSecondary = Color(0xFF022C22),
            secondaryContainer = Color(0xFF065F46),
            onSecondaryContainer = Color(0xFFD1FAE5),
            tertiary = Color(0xFFA78BFA),
            onTertiary = Color(0xFF2E1065),
            tertiaryContainer = Color(0xFF4C1D95),
            onTertiaryContainer = Color(0xFFEDE9FE),
            error = Color(0xFFF87171),
            onError = Color(0xFF7F1D1D),
            errorContainer = Color(0xFF991B1B),
            onErrorContainer = Color(0xFFFECACA),
            background = Color(0xFF0B0F19),
            onBackground = Color(0xFFF8FAFC),
            surface = Color(0xFF111827),
            onSurface = Color(0xFFF1F5F9),
            surfaceVariant = Color(0xFF1E293B),
            onSurfaceVariant = Color(0xFF94A3B8),
            inverseOnSurface = Color(0xFF0B0F19),
            inverseSurface = Color(0xFFF1F5F9),
            inversePrimary = Color(0xFF3B82F6),
            surfaceContainerLowest = Color(0xFF0B0F19),
            surfaceContainerLow = Color(0xFF0F172A),
            surfaceContainer = Color(0xFF111827),
            surfaceContainerHigh = Color(0xFF1E293B),
            surfaceContainerHighest = Color(0xFF334155),
            outline = Color(0xFF475569),
            outlineVariant = Color(0xFF1E293B),
        )
    }
}