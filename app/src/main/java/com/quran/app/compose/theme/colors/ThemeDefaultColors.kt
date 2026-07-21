package com.quran.app.compose.theme.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class ThemeDefaultColors : BaseColors() {
    override fun lightColors(): ColorScheme {
        return lightColorScheme(
            primary = Color(0xFF006C47),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFF8EF8C1),
            onPrimaryContainer = Color(0xFF002112),
            secondary = Color(0xFF7D581A),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFDDB1),
            onSecondaryContainer = Color(0xFF291800),
            tertiary = Color(0xFF3D6373),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFC1E8FB),
            onTertiaryContainer = Color(0xFF001F29),
            error = Color(0xFFBA1A1A),
            onError = Color(0xFFFFFFFF),
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF410002),
            background = Color(0xFFF4FAF5),
            onBackground = Color(0xFF171D1A),
            surface = Color(0xFFFAFDF9),
            onSurface = Color(0xFF171D1A),
            surfaceVariant = Color(0xFFDBE5DE),
            onSurfaceVariant = Color(0xFF404944),
            inverseOnSurface = Color(0xFFF0F1F0),
            inverseSurface = Color(0xFF2E3130),
            inversePrimary = Color(0xFF72DBA7),
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF0F5F1),
            surfaceContainer = Color(0xFFEAF0EB),
            surfaceContainerHigh = Color(0xFFE4EAE5),
            surfaceContainerHighest = Color(0xFFDEE4DF),
            outline = Color(0xFF707974),
            outlineVariant = Color(0xFFBFC9C2),
        )
    }

    override fun darkColors(): ColorScheme {
        return darkColorScheme(
            primary = Color(0xFF72DBA7),
            onPrimary = Color(0xFF003823),
            primaryContainer = Color(0xFF005234),
            onPrimaryContainer = Color(0xFF8EF8C1),
            secondary = Color(0xFFF0BD7A),
            onSecondary = Color(0xFF452B00),
            secondaryContainer = Color(0xFF604104),
            onSecondaryContainer = Color(0xFFFFDDB1),
            tertiary = Color(0xFFF5BE8F),
            onTertiary = Color(0xFF3E2D00),
            tertiaryContainer = Color(0xFF594100),
            onTertiaryContainer = Color(0xFFFFF0E4),
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            background = Color(0xFF0E1511),
            onBackground = Color(0xFFDEE4DF),
            surface = Color(0xFF131B16),
            onSurface = Color(0xFFDEE4DF),
            surfaceVariant = Color(0xFF404944),
            onSurfaceVariant = Color(0xFFBFC9C2),
            inverseOnSurface = Color(0xFF0E1511),
            inverseSurface = Color(0xFFDEE4DF),
            inversePrimary = Color(0xFF006C47),
            surfaceContainerLowest = Color(0xFF090F0C),
            surfaceContainerLow = Color(0xFF171D19),
            surfaceContainer = Color(0xFF1B211D),
            surfaceContainerHigh = Color(0xFF252C27),
            surfaceContainerHighest = Color(0xFF303732),
            outline = Color(0xFF89938D),
            outlineVariant = Color(0xFF404944),
        )
    }
}