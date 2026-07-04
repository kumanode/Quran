package com.quranapp.android.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeRedColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFFE11D48), // Rose
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFFFE4E6),
            onPrimaryContainer = Color(0xFF881337),
            secondary = Color(0xFFF59E0B),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFEF3C7),
            onSecondaryContainer = Color(0xFF78350F),
            tertiary = Color(0xFF8B5CF6),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFEDE9FE),
            onTertiaryContainer = Color(0xFF4C1D95),
            error = Color(0xFFDC2626),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFEE2E2),
            onErrorContainer = Color(0xFF991B1B),
            background = Color(0xFFFDF8F9),
            onBackground = Color(0xFF1C1315),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF261A1D),
            surfaceVariant = Color(0xFFF5EBEF),
            onSurfaceVariant = Color(0xFF544449),
            inverseOnSurface = Color(0xFFFDF8F9),
            inverseSurface = Color(0xFF261A1D),
            inversePrimary = Color(0xFFFB7185),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFFDF8F9),
            surfaceContainer = Color(0xFFFFFFFF),
            surfaceContainerHigh = Color(0xFFF5EBEF),
            surfaceContainerHighest = Color(0xFFEBE0E3),
            outline = Color(0xFFD4C3C8),
            outlineVariant = Color(0xFFEBE0E3),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFFB7185),
            onPrimary = Color(0xFF4C0519),
            primaryContainer = Color(0xFF881337),
            onPrimaryContainer = Color(0xFFFFE4E6),
            secondary = Color(0xFFFBBF24),
            onSecondary = Color(0xFF451A03),
            secondaryContainer = Color(0xFF78350F),
            onSecondaryContainer = Color(0xFFFEF3C7),
            tertiary = Color(0xFFA78BFA),
            onTertiary = Color(0xFF2E1065),
            tertiaryContainer = Color(0xFF4C1D95),
            onTertiaryContainer = Color(0xFFEDE9FE),
            error = Color(0xFFF87171),
            onError = Color(0xFF7F1D1D),
            errorContainer = Color(0xFF991B1B),
            onErrorContainer = Color(0xFFFECACA),
            background = Color(0xFF130A0D),
            onBackground = Color(0xFFF3E7EA),
            surface = Color(0xFF1A1013),
            onSurface = Color(0xFFEBE0E3),
            surfaceVariant = Color(0xFF2A1C21),
            onSurfaceVariant = Color(0xFFB5A1A7),
            inverseOnSurface = Color(0xFF130A0D),
            inverseSurface = Color(0xFFEBE0E3),
            inversePrimary = Color(0xFFE11D48),
            surfaceContainerLowest = Color(0xFF130A0D),
            surfaceContainerLow = Color(0xFF160D10),
            surfaceContainer = Color(0xFF1A1013),
            surfaceContainerHigh = Color(0xFF2A1C21),
            surfaceContainerHighest = Color(0xFF3D2A32),
            outline = Color(0xFF756269),
            outlineVariant = Color(0xFF3D2A32),
        )
    }
}