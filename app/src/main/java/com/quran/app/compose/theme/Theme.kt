package com.quran.app.compose.theme

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
import com.quran.app.compose.theme.getAppTypography
import com.quran.app.compose.utils.LocalAppLocale
import com.quran.app.compose.utils.ThemeUtils
import com.quran.app.compose.utils.appLocaleFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.material3.ColorScheme

import androidx.compose.runtime.Stable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.quran.app.compose.utils.LocalLiquidGlassEffect

@Stable
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
    val rawColorScheme = ThemeUtils.observeColorScheme(context, isDarkTheme)
    val colorScheme = rawColorScheme.animate()
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
                    isAppearanceLightStatusBars = !isDarkTheme
                    isAppearanceLightNavigationBars = !isDarkTheme
                }
            }
        }
    }

    val isLiquidGlass = ThemeUtils.observeIsLiquidGlassEffect()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    val isResumed = lifecycleState.isAtLeast(Lifecycle.State.RESUMED)
    val shouldAnimate = isLiquidGlass && isResumed

    val time = if (shouldAnimate) {
        val infiniteTransition = rememberInfiniteTransition(label = "liquid_glass_bg")
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2f * Math.PI.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(25000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "time"
        ).value
    } else {
        0f
    }

    CompositionLocalProvider(
        LocalAppLocale provides appLocale,
        LocalLiquidGlassEffect provides isLiquidGlass
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = getAppTypography(),
            content = {
                val backgroundModifier = if (shouldAnimate) {
                    Modifier.drawBehind {
                        val width = size.width
                        val height = size.height

                        // Base color background
                        drawRect(colorScheme.surfaceContainerLowest)

                        // Blob 1 (Primary Container)
                        val x1 = width * (0.5f + 0.3f * kotlin.math.cos(time.toDouble()).toFloat())
                        val y1 = height * (0.4f + 0.2f * kotlin.math.sin(time.toDouble()).toFloat())
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(colorScheme.primaryContainer.copy(alpha = 0.4f), Color.Transparent),
                                center = Offset(x1, y1),
                                radius = width * 0.75f
                            ),
                            center = Offset(x1, y1),
                            radius = width * 0.75f
                        )

                        // Blob 2 (Tertiary Container)
                        val x2 = width * (0.3f + 0.25f * kotlin.math.sin((time + 2f).toDouble()).toFloat())
                        val y2 = height * (0.6f + 0.25f * kotlin.math.cos((time + 1f).toDouble()).toFloat())
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(colorScheme.tertiaryContainer.copy(alpha = 0.4f), Color.Transparent),
                                center = Offset(x2, y2),
                                radius = width * 0.8f
                            ),
                            center = Offset(x2, y2),
                            radius = width * 0.8f
                        )

                        // Blob 3 (Secondary Container)
                        val x3 = width * (0.7f + 0.2f * kotlin.math.cos((time - 1f).toDouble()).toFloat())
                        val y3 = height * (0.3f + 0.2f * kotlin.math.sin((time + 3f).toDouble()).toFloat())
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(colorScheme.secondaryContainer.copy(alpha = 0.25f), Color.Transparent),
                                center = Offset(x3, y3),
                                radius = width * 0.65f
                            ),
                            center = Offset(x3, y3),
                            radius = width * 0.65f
                        )
                    }
                } else if (isLiquidGlass) {
                    Modifier.background(colorScheme.surfaceContainerLowest)
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

