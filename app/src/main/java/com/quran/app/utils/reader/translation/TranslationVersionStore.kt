package com.quran.app.utils.reader.translation

import android.content.Context
import com.quran.app.api.JsonHelper
import com.quran.app.utils.Log
import com.quran.app.utils.app.AppUtils
import com.quran.app.utils.univ.FileUtils
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.io.File

@Serializable
data class TranslationVersionEntry(
    val version: Long = 0,
)

class TranslationVersionStore(context: Context) {
    private val appContext = context.applicationContext
    private val fileUtils = FileUtils.newInstance(appContext)

    private fun getStoreFile(): File {
        val dir = FileUtils.makeAndGetAppResourceDir(AppUtils.APP_OTHER_DIR)
        return File(dir, "translation_versions.json")
    }

    fun loadAll(): MutableMap<String, TranslationVersionEntry> {
        val file = getStoreFile()
        if (!file.exists() || file.length() == 0L) {
            return mutableMapOf()
        }

        return try {
            JsonHelper.json.decodeFromString<Map<String, TranslationVersionEntry>>(
                file.readText(Charsets.UTF_8),
            ).toMutableMap()
        } catch (e: Exception) {
            Log.saveError(e, "TranslationVersionStore.loadAll")
            mutableMapOf()
        }
    }

    fun saveAll(data: Map<String, TranslationVersionEntry>) {
        val file = getStoreFile()
        try {
            if (!fileUtils.createFile(file)) return
            file.writeText(JsonHelper.json.encodeToString(data), Charsets.UTF_8)
        } catch (e: Exception) {
            Log.saveError(e, "TranslationVersionStore.saveAll")
        }
    }
}
