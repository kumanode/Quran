package com.quran.app.utils.mediaplayer

import android.content.Context
import com.quran.app.api.JsonHelper
import com.quran.app.utils.Log
import com.quran.app.utils.mediaplayer.RecitationModelManager.Companion.getRecitationsDir
import com.quran.app.utils.univ.FileUtils
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.io.File

@Serializable
data class ReciterStoredVersions(
    val audio: Int = 1,
    val timing: Int = 0,
)

class RecitationVersionStore(context: Context) {
    private val appContext = context.applicationContext
    private val fileUtils = FileUtils.newInstance(appContext)

    private fun getStoreFile(): File {
        return File(getRecitationsDir(), "reciter_versions.json")
    }

    fun loadAll(): MutableMap<String, ReciterStoredVersions> {
        val file = getStoreFile()

        if (!file.exists() || file.length() == 0L) {
            return mutableMapOf()
        }

        return try {
            JsonHelper.json.decodeFromString<Map<String, ReciterStoredVersions>>(
                file.readText(Charsets.UTF_8),
            ).toMutableMap()
        } catch (e: Exception) {
            Log.saveError(e, "RecitationVersionStore.loadAll")
            mutableMapOf()
        }
    }

    fun saveAll(data: Map<String, ReciterStoredVersions>) {
        val file = getStoreFile()
        try {
            if (!fileUtils.createFile(file)) return
            file.writeText(
                JsonHelper.json.encodeToString(data),
                Charsets.UTF_8,
            )
        } catch (e: Exception) {
            Log.saveError(e, "RecitationVersionStore.saveAll")
        }
    }

    fun getStoredVersions(reciterId: String): ReciterStoredVersions? {
        return loadAll()[reciterId]
    }

    fun setStoredVersions(reciterId: String, audio: Int, timing: Int) {
        val all = loadAll()
        all[reciterId] = ReciterStoredVersions(audio = audio, timing = timing)
        saveAll(all)
    }
}
