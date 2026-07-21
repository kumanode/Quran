package com.quran.app.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeYellowColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF825500),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFFFDDB3),
            onPrimaryContainer = Color(0xFF291800),
            secondary = Color(0xFF765A00),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFE08F),
            onSecondaryContainer = Color(0xFF241A00),
            tertiary = Color(0xFF4A6547),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFCCEBC5),
            onTertiaryContainer = Color(0xFF08210A),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFFFFBF7),
            onBackground = Color(0xFF1F1B16),
            surface = Color(0xFFFCF9F5),
            onSurface = Color(0xFF1F1B16),
            surfaceVariant = Color(0xFFF0E1CF),
            onSurfaceVariant = Color(0xFF4F4539),
            inverseOnSurface = Color(0xFFF9F0E7),
            inverseSurface = Color(0xFF352F29),
            inversePrimary = Color(0xFFFFB951),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFFBF4EB),
            surfaceContainer = Color(0xFFF5ECE2),
            surfaceContainerHigh = Color(0xFFEFE7DC),
            surfaceContainerHighest = Color(0xFFE9E1D6),
            outline = Color(0xFF817567),
            outlineVariant = Color(0xFFD3C5B4),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFFFFB951),
            onPrimary = Color(0xFF452B00),
            primaryContainer = Color(0xFF633F00),
            onPrimaryContainer = Color(0xFFFFDDB3),
            secondary = Color(0xFFE9C248),
            onSecondary = Color(0xFF3E2E00),
            secondaryContainer = Color(0xFF594300),
            onSecondaryContainer = Color(0xFFFFE08F),
            tertiary = Color(0xFFB1CFA9),
            onTertiary = Color(0xFF1D361C),
            tertiaryContainer = Color(0xFF334D31),
            onTertiaryContainer = Color(0xFFCCEBC5),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF1A140E),
            onBackground = Color(0xFFEFE1D6),
            surface = Color(0xFF221A13),
            onSurface = Color(0xFFEFE1D6),
            surfaceVariant = Color(0xFF4F4539),
            onSurfaceVariant = Color(0xFFD3C5B4),
            inverseOnSurface = Color(0xFF1A140E),
            inverseSurface = Color(0xFFEFE1D6),
            inversePrimary = Color(0xFF825500),
            surfaceContainerLowest = Color(0xFF140D08),
            surfaceContainerLow = Color(0xFF292017),
            surfaceContainer = Color(0xFF31281F),
            surfaceContainerHigh = Color(0xFF3C3329),
            surfaceContainerHighest = Color(0xFF483E34),
            outline = Color(0xFF9C8F80),
            outlineVariant = Color(0xFF4F4539),
        )
    }
}