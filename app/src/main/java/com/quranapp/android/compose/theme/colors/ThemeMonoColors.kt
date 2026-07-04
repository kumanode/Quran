package com.quranapp.android.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeMonoColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF374151), // Graphite
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFF3F4F6),
            onPrimaryContainer = Color(0xFF111827),
            secondary = Color(0xFF6B7280),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFE5E7EB),
            onSecondaryContainer = Color(0xFF1F2937),
            tertiary = Color(0xFF4B5563),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFF9FAFB),
            onTertiaryContainer = Color(0xFF374151),
            error = Color(0xFFEF4444),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFEE2E2),
            onErrorContainer = Color(0xFF991B1B),
            background = Color(0xFFF9FAFB),
            onBackground = Color(0xFF111827),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF1F2937),
            surfaceVariant = Color(0xFFF3F4F6),
            onSurfaceVariant = Color(0xFF4B5563),
            inverseOnSurface = Color(0xFFF9FAFB),
            inverseSurface = Color(0xFF1F2937),
            inversePrimary = Color(0xFF9CA3AF),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF9FAFB),
            surfaceContainer = Color(0xFFFFFFFF),
            surfaceContainerHigh = Color(0xFFF3F4F6),
            surfaceContainerHighest = Color(0xFFE5E7EB),
            outline = Color(0xFFD1D5DB),
            outlineVariant = Color(0xFFE5E7EB),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFD1D5DB),
            onPrimary = Color(0xFF1F2937),
            primaryContainer = Color(0xFF374151),
            onPrimaryContainer = Color(0xFFF3F4F6),
            secondary = Color(0xFF9CA3AF),
            onSecondary = Color(0xFF111827),
            secondaryContainer = Color(0xFF4B5563),
            onSecondaryContainer = Color(0xFFE5E7EB),
            tertiary = Color(0xFFE5E7EB),
            onTertiary = Color(0xFF111827),
            tertiaryContainer = Color(0xFF374151),
            onTertiaryContainer = Color(0xFFF9FAFB),
            error = Color(0xFFF87171),
            onError = Color(0xFF7F1D1D),
            errorContainer = Color(0xFF991B1B),
            onErrorContainer = Color(0xFFFECACA),
            background = Color(0xFF0F1216), // Deep Dark Gray
            onBackground = Color(0xFFF9FAFB),
            surface = Color(0xFF181C22),
            onSurface = Color(0xFFE5E7EB),
            surfaceVariant = Color(0xFF272C36),
            onSurfaceVariant = Color(0xFF9CA3AF),
            inverseOnSurface = Color(0xFF0F1216),
            inverseSurface = Color(0xFFE5E7EB),
            inversePrimary = Color(0xFF374151),
            surfaceContainerLowest = Color(0xFF0F1216),
            surfaceContainerLow = Color(0xFF12151B),
            surfaceContainer = Color(0xFF181C22),
            surfaceContainerHigh = Color(0xFF272C36),
            surfaceContainerHighest = Color(0xFF38404E),
            outline = Color(0xFF6B7280),
            outlineVariant = Color(0xFF38404E),
        )
    }
}