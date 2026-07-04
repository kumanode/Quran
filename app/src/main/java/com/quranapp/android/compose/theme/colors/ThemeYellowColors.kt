package com.quranapp.android.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeYellowColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFFB45309), // Dark Amber
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFFEF3C7),
            onPrimaryContainer = Color(0xFF78350F),
            secondary = Color(0xFF059669),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFD1FAE5),
            onSecondaryContainer = Color(0xFF064E3B),
            tertiary = Color(0xFFEA580C),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFFFEDD5),
            onTertiaryContainer = Color(0xFF7C2D12),
            error = Color(0xFFEF4444),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFEE2E2),
            onErrorContainer = Color(0xFF991B1B),
            background = Color(0xFFFFFBEB),
            onBackground = Color(0xFF1F1809),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF2B2212),
            surfaceVariant = Color(0xFFFCEECC),
            onSurfaceVariant = Color(0xFF5E4E2C),
            inverseOnSurface = Color(0xFFFFFBEB),
            inverseSurface = Color(0xFF2B2212),
            inversePrimary = Color(0xFFFBBF24),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFFFFBEB),
            surfaceContainer = Color(0xFFFFFFFF),
            surfaceContainerHigh = Color(0xFFFCEECC),
            surfaceContainerHighest = Color(0xFFF7E2AC),
            outline = Color(0xFFDEC58E),
            outlineVariant = Color(0xFFF7E2AC),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFFBBF24),
            onPrimary = Color(0xFF451A03),
            primaryContainer = Color(0xFF78350F),
            onPrimaryContainer = Color(0xFFFEF3C7),
            secondary = Color(0xFF34D399),
            onSecondary = Color(0xFF022C22),
            secondaryContainer = Color(0xFF065F46),
            onSecondaryContainer = Color(0xFFD1FAE5),
            tertiary = Color(0xFFFB923C),
            onTertiary = Color(0xFF431407),
            tertiaryContainer = Color(0xFF7C2D12),
            onTertiaryContainer = Color(0xFFFFEDD5),
            error = Color(0xFFF87171),
            onError = Color(0xFF7F1D1D),
            errorContainer = Color(0xFF991B1B),
            onErrorContainer = Color(0xFFFECACA),
            background = Color(0xFF141006),
            onBackground = Color(0xFFFDF7E7),
            surface = Color(0xFF1F180B),
            onSurface = Color(0xFFF5EBD0),
            surfaceVariant = Color(0xFF332712),
            onSurfaceVariant = Color(0xFFC7B183),
            inverseOnSurface = Color(0xFF141006),
            inverseSurface = Color(0xFFF5EBD0),
            inversePrimary = Color(0xFFD97706),
            surfaceContainerLowest = Color(0xFF141006),
            surfaceContainerLow = Color(0xFF1A1408),
            surfaceContainer = Color(0xFF1F180B),
            surfaceContainerHigh = Color(0xFF332712),
            surfaceContainerHighest = Color(0xFF47371A),
            outline = Color(0xFF876C3B),
            outlineVariant = Color(0xFF47371A),
        )
    }
}