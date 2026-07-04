package com.quranapp.android.compose.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.quranapp.android.compose.theme.getAppTypography
import com.quranapp.android.compose.utils.LocalAppLocale
import com.quranapp.android.compose.utils.ThemeUtils
import com.quranapp.android.compose.utils.appLocaleFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.ColorScheme

@Composable
fun ColorScheme.animate(): ColorScheme {
    val animationSpec = tween<Color>(durationMillis = 400)
    return ColorScheme(
        primary = animateColorAsState(primary, animationSpec, label = "").value,
        onPrimary = animateColorAsState(onPrimary, animationSpec, label = "").value,
        primaryContainer = animateColorAsState(primaryContainer, animationSpec, label = "").value,
        onPrimaryContainer = animateColorAsState(onPrimaryContainer, animationSpec, label = "").value,
        inversePrimary = animateColorAsState(inversePrimary, animationSpec, label = "").value,
        secondary = animateColorAsState(secondary, animationSpec, label = "").value,
        onSecondary = animateColorAsState(onSecondary, animationSpec, label = "").value,
        secondaryContainer = animateColorAsState(secondaryContainer, animationSpec, label = "").value,
        onSecondaryContainer = animateColorAsState(onSecondaryContainer, animationSpec, label = "").value,
        tertiary = animateColorAsState(tertiary, animationSpec, label = "").value,
        onTertiary = animateColorAsState(onTertiary, animationSpec, label = "").value,
        tertiaryContainer = animateColorAsState(tertiaryContainer, animationSpec, label = "").value,
        onTertiaryContainer = animateColorAsState(onTertiaryContainer, animationSpec, label = "").value,
        background = animateColorAsState(background, animationSpec, label = "").value,
        onBackground = animateColorAsState(onBackground, animationSpec, label = "").value,
        surface = animateColorAsState(surface, animationSpec, label = "").value,
        onSurface = animateColorAsState(onSurface, animationSpec, label = "").value,
        surfaceVariant = animateColorAsState(surfaceVariant, animationSpec, label = "").value,
        onSurfaceVariant = animateColorAsState(onSurfaceVariant, animationSpec, label = "").value,
        surfaceTint = animateColorAsState(surfaceTint, animationSpec, label = "").value,
        inverseSurface = animateColorAsState(inverseSurface, animationSpec, label = "").value,
        inverseOnSurface = animateColorAsState(inverseOnSurface, animationSpec, label = "").value,
        error = animateColorAsState(error, animationSpec, label = "").value,
        onError = animateColorAsState(onError, animationSpec, label = "").value,
        errorContainer = animateColorAsState(errorContainer, animationSpec, label = "").value,
        onErrorContainer = animateColorAsState(onErrorContainer, animationSpec, label = "").value,
        outline = animateColorAsState(outline, animationSpec, label = "").value,
        outlineVariant = animateColorAsState(outlineVariant, animationSpec, label = "").value,
        scrim = animateColorAsState(scrim, animationSpec, label = "").value,
        surfaceBright = animateColorAsState(surfaceBright, animationSpec, label = "").value,
        surfaceDim = animateColorAsState(surfaceDim, animationSpec, label = "").value,
        surfaceContainer = animateColorAsState(surfaceContainer, animationSpec, label = "").value,
        surfaceContainerHigh = animateColorAsState(surfaceContainerHigh, animationSpec, label = "").value,
        surfaceContainerHighest = animateColorAsState(surfaceContainerHighest, animationSpec, label = "").value,
        surfaceContainerLow = animateColorAsState(surfaceContainerLow, animationSpec, label = "").value,
        surfaceContainerLowest = animateColorAsState(surfaceContainerLowest, animationSpec, label = "").value,
    )
}

@Composable
fun QuranAppTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current

    val isDarkTheme = ThemeUtils.observeDarkTheme()
    val colorScheme = ThemeUtils.observeColorScheme(context, isDarkTheme).animate()
    val appLocale by appLocaleFlow.collectAsState()

    if (!view.isInEditMode) {
        SideEffect {
            var ctx = view.context
            while (ctx is android.content.ContextWrapper) {
                if (ctx is Activity) break
                ctx = ctx.baseContext
            }
            val window = (ctx as? Activity)?.window

            if (window != null) {
                WindowCompat.getInsetsController(window, view).apply {
                    isAppearanceLightStatusBars = false  // Always white icons on colored gradient app bar
                    isAppearanceLightNavigationBars = !isDarkTheme
                }
            }
        }
    }

    val isLiquidGlass = ThemeUtils.observeIsLiquidGlassEffect()

    CompositionLocalProvider(LocalAppLocale provides appLocale) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = getAppTypography(),
            content = {
                val backgroundModifier = if (isLiquidGlass) {
                    // Create a dynamic gradient using the active color scheme's accent colors
                    val gradientColors = listOf(
                        colorScheme.surfaceContainerLowest.copy(alpha = 1f),
                        colorScheme.primaryContainer.copy(alpha = 1f),
                        colorScheme.tertiaryContainer.copy(alpha = 1f),
                        colorScheme.surfaceContainer.copy(alpha = 1f)
                    )
                    Modifier.background(Brush.linearGradient(gradientColors))
                } else {
                    Modifier
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(backgroundModifier)
                ) {
                    content()
                }
            }
        )
    }

}

