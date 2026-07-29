package com.quran.app.components.quran

import com.quran.app.compose.utils.appFallbackLanguageCodes
import com.quran.app.utils.Log
import java.io.Serializable

data class QuranScienceItem(
    private val title: String,
    val referencesCount: Int,
    val path: String,
    val drawableRes: Int,
    val translations: Map<String, String>
) : Serializable {
    fun getTitle(): String {
        return appFallbackLanguageCodes().firstNotNullOfOrNull {
            translations.get(it)
        } ?: title
    }
}
