package com.quran.app.utils.tafsir

import com.quran.app.api.models.tafsir.TafsirModel
import com.quran.app.compose.utils.appFallbackLanguageCodes
import com.quran.app.utils.app.AppUtils
import com.quran.app.utils.reader.tafsir.TafsirManager
import com.quran.app.utils.univ.FileUtils

object TafsirUtils {
    @JvmField
    val DIR_NAME: String =
        FileUtils.createPath(AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR, "tafsirs")

    const val AVAILABLE_TAFSIRS_FILENAME = "available_tafsirs_v2.json"
    const val KEY_TAFSIR = "key.tafsir"

    fun getDefaultTafsirKey(): String? {
        val models = TafsirManager.getModels()?.takeIf { it.isNotEmpty() } ?: return null

        val idealModels = appFallbackLanguageCodes().firstNotNullOfOrNull {
            models[it]
        } ?: models["en"]

        return idealModels?.firstOrNull()?.key
    }

    fun isUrdu(key: String): Boolean {
        val model = TafsirManager.getModel(key) ?: return false
        return model.langCode == "ur"
    }

    fun isArabic(key: String): Boolean {
        val model = TafsirManager.getModel(key) ?: return false
        return model.langCode == "ar"
    }

    fun tafsirVerseRangeInChapter(tafsir: TafsirModel, chapterNo: Int): IntRange? {
        val nums = tafsir.verses.mapNotNull { key ->
            val parts = key.split(":")
            if (parts.size < 2) return@mapNotNull null
            val surah = parts[0].toIntOrNull() ?: return@mapNotNull null
            val ayah = parts[1].toIntOrNull() ?: return@mapNotNull null
            if (surah == chapterNo) ayah else null
        }
        if (nums.isEmpty()) return null
        val sorted = nums.sorted()
        return sorted.first()..sorted.last()
    }
}
