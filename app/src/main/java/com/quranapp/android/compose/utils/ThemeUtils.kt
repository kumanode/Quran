package com.quranapp.android.compose.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.compose.ui.graphics.Color
import com.quranapp.android.compose.theme.colors.BaseColors
import com.quranapp.android.compose.theme.colors.ThemeBlueColors
import com.quranapp.android.compose.theme.colors.ThemeDefaultColors
import com.quranapp.android.compose.theme.colors.ThemeMonoColors
import com.quranapp.android.compose.theme.colors.ThemePurpleColors
import com.quranapp.android.compose.theme.colors.ThemeRedColors
import com.quranapp.android.compose.theme.colors.ThemeVioletColors
import com.quranapp.android.compose.theme.colors.ThemeYellowColors
import com.quranapp.android.compose.utils.preferences.DataStoreManager
import com.quranapp.android.R
import com.quranapp.android.compose.utils.ThemeUtils.observeDarkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

object ThemeUtils {
    const val THEME_MODE_DEFAULT = "app.theme.default"
    const val THEME_MODE_LIGHT = "app.theme.light"
    const val THEME_MODE_DARK = "app.theme.dark"

    private val KEY_THEME_MODE = stringPreferencesKey("v2.theme_mode")
    private val KEY_THEME_COLOR = stringPreferencesKey("v2.theme_color")
    private val KEY_THEME_DYNAMIC_COLOR = booleanPreferencesKey("v2.theme_dynamic_color")
    private val KEY_THEME_LIQUID_GLASS = booleanPreferencesKey("v2.theme_liquid_glass_effect")

    const val THEME_COLOR_DEFAULT = "default"
    const val THEME_COLOR_BLUE = "blue"
    const val THEME_COLOR_RED = "red"
    const val THEME_COLOR_PURPLE = "purple"
    const val THEME_COLOR_MONO = "mono"
    const val THEME_COLOR_VIOLET = "violet"
    const val THEME_COLOR_YELLOW = "yellow"

    const val DEFAULT_DYNAMIC_COLOR = false

    fun isDynamicColorSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    fun resolveThemeModeLabel(themeMode: String): Int {
        return when (themeMode) {
            THEME_MODE_LIGHT -> R.string.strLabelThemeLight
            THEME_MODE_DARK -> R.string.strLabelThemeDark
            else -> R.string.strLabelSystemDefault
        }
    }

    @Composable
    fun observeDarkTheme(): Boolean {
        val themeMode = observeThemeMode()

        return when (themeMode) {
            THEME_MODE_LIGHT -> false
            THEME_MODE_DARK -> true
            else -> isSystemInDarkTheme()
        }
    }

    @Composable
    fun observeThemeMode(): String {
        return DataStoreManager.observe(KEY_THEME_MODE, THEME_MODE_DEFAULT)
    }

    fun getThemeMode(): String {
        return DataStoreManager.read(KEY_THEME_MODE, THEME_MODE_DEFAULT)
    }

    suspend fun setThemeMode(themeMode: String) {
        DataStoreManager.write(KEY_THEME_MODE, themeMode)
    }

    @Composable
    fun observeThemeColor(): String {
        return DataStoreManager.observe(KEY_THEME_COLOR, THEME_COLOR_DEFAULT)
    }

    suspend fun setThemeColor(themeColor: String) {
        DataStoreManager.write(KEY_THEME_COLOR, themeColor)
    }

    @Composable
    fun observeIsDynamicColor(): Boolean {
        return DataStoreManager.observe(KEY_THEME_DYNAMIC_COLOR, DEFAULT_DYNAMIC_COLOR)
    }

    suspend fun setDynamicColor(isDynamicColor: Boolean) {
        DataStoreManager.write(KEY_THEME_DYNAMIC_COLOR, isDynamicColor)
    }

    @Composable
    fun observeIsLiquidGlassEffect(): Boolean {
        // Fallback for migration: if color was liquid_glass, consider effect ON initially (DataStore returns false by default if not set).
        // Since observe doesn't let us map easily here without breaking composition simplicity, we rely on the migration on startup or user toggle.
        return DataStoreManager.observe(KEY_THEME_LIQUID_GLASS, false)
    }

    suspend fun setIsLiquidGlassEffect(isLiquidGlass: Boolean) {
        DataStoreManager.write(KEY_THEME_LIQUID_GLASS, isLiquidGlass)
    }

    @Composable
    fun observeColorScheme(
        context: Context,
        isDarkTheme: Boolean = observeDarkTheme()
    ): ColorScheme {
        val themeColor = observeThemeColor()
        val isDynamicColor = observeIsDynamicColor()
        val isLiquidGlass = observeIsLiquidGlassEffect()
        return buildColorScheme(context, isDarkTheme, themeColor, isDynamicColor, isLiquidGlass)
    }

    fun colorSchemeFromPreferences(context: Context, isDark: Boolean? = null): ColorScheme {
        return buildColorScheme(
            context,
            isDark ?: isDarkTheme(context),
            DataStoreManager.read(KEY_THEME_COLOR, THEME_COLOR_DEFAULT),
            DataStoreManager.read(KEY_THEME_DYNAMIC_COLOR, DEFAULT_DYNAMIC_COLOR),
            DataStoreManager.read(KEY_THEME_LIQUID_GLASS, false),
        )
    }

    fun widgetAppearancePreferencesFlow(): Flow<Triple<String, String, Boolean>> {
        return combine(
            DataStoreManager.flow(KEY_THEME_MODE, THEME_MODE_DEFAULT),
            DataStoreManager.flow(KEY_THEME_COLOR, THEME_COLOR_DEFAULT),
            DataStoreManager.flow(KEY_THEME_DYNAMIC_COLOR, DEFAULT_DYNAMIC_COLOR),
        ) { mode, color, dynamicColor ->
            Triple(mode, color, dynamicColor)
        }.distinctUntilChanged()
    }

    private fun buildColorScheme(
        context: Context,
        isDarkTheme: Boolean,
        themeColor: String,
        isDynamicColor: Boolean,
        isLiquidGlass: Boolean,
    ): ColorScheme {
        // Dynamic color is available on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && isDynamicColor) {
            val scheme = if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            return if (isLiquidGlass) scheme.withLiquidGlass(isDarkTheme) else scheme
        }

        val preferredColor: BaseColors = when (themeColor) {
            THEME_COLOR_BLUE -> ThemeBlueColors()
            THEME_COLOR_RED -> ThemeRedColors()
            THEME_COLOR_PURPLE -> ThemePurpleColors()
            THEME_COLOR_MONO -> ThemeMonoColors()
            THEME_COLOR_VIOLET -> ThemeVioletColors()
            THEME_COLOR_YELLOW -> ThemeYellowColors()
            // Migration fallback
            "liquid_glass" -> ThemeDefaultColors()
            else -> ThemeDefaultColors()
        }

        val scheme = if (isDarkTheme) preferredColor.darkColors() else preferredColor.lightColors()
        return if (isLiquidGlass || themeColor == "liquid_glass") scheme.withLiquidGlass(isDarkTheme) else scheme
    }

    private fun ColorScheme.withLiquidGlass(isDarkTheme: Boolean): ColorScheme {
        return copy(
            background = Color.Transparent,
            surface = surface.copy(alpha = if (isDarkTheme) 0.53f else 0.73f),
            surfaceContainerLowest = surfaceContainerLowest.copy(alpha = if (isDarkTheme) 0.2f else 0.33f),
            surfaceContainerLow = surfaceContainerLow.copy(alpha = if (isDarkTheme) 0.33f else 0.47f),
            surfaceContainer = surfaceContainer.copy(alpha = if (isDarkTheme) 0.67f else 0.87f),
            surfaceContainerHigh = surfaceContainerHigh.copy(alpha = if (isDarkTheme) 0.8f else 0.93f),
            surfaceContainerHighest = surfaceContainerHighest.copy(alpha = if (isDarkTheme) 0.93f else 1f),
            outline = outline.copy(alpha = if (isDarkTheme) 0.53f else 0.67f),
            outlineVariant = outlineVariant.copy(alpha = if (isDarkTheme) 0.33f else 0.47f),
        )
    }

    /**
     * Dark/light resolution aligned with [observeDarkTheme] (theme mode + system night).
     */
    fun isDarkTheme(context: Context): Boolean {
        return when (getThemeMode()) {
            THEME_MODE_LIGHT -> false
            THEME_MODE_DARK -> true
            else -> {
                val uiMode =
                    context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                uiMode == Configuration.UI_MODE_NIGHT_YES
            }
        }
    }

    fun resolveThemeModeForDelegate(themeMode: String? = null): Int {
        return when (themeMode ?: getThemeMode()) {
            THEME_MODE_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            THEME_MODE_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            THEME_MODE_DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }
}
