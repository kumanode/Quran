package com.quran.app.utils.reader

import android.content.Context
import android.util.Pair
import com.quran.app.R
import com.quran.app.api.models.translation.TranslationBookInfoModel
import com.quran.app.components.transls.TranslModel
import com.quran.app.utils.Log
import com.quran.app.utils.Logger
import com.quran.app.utils.app.AppUtils
import com.quran.app.utils.univ.FileUtils
import com.quran.app.utils.univ.MessageUtils
import com.quran.app.utils.univ.StringUtils
import org.json.JSONObject
import java.io.File

object TranslUtils {
    @JvmField
    val DIR_NAME = FileUtils.createPath(
        AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR,
        "translations",
    )
    @JvmField
    val DIR_NAME_4_AVAILABLE_DOWNLOADS = FileUtils.createPath(
        AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR,
        "available_translation_downloads",
    )
    const val TRANSL_INFO_FILE_NAME = "manifest.json"
    const val KEY_TRANSLATIONS = "key.translations"
    const val KEY_NEW_TRANSLATIONS = "key.translations_new"
    const val TRANSL_FILE_NAME_FORMAT = "translation_%d_%s_%s.json"
    const val TRANSL_AVAILABLE_DOWNLOADS_FILE_NAME = "available_downloads.json"

    const val TRANSL_SLUG_EN_SAHIH_INTERNATIONAL = "en_101_sahih-international"
    const val TRANSL_SLUG_EN_THE_CLEAR_QURAN = "en_102_the-clear-quran"
    const val TRANSL_SLUG_UR_JUNAGARHI = "ur_201_junagarhi"
    const val TRANSL_SLUG_ID_INDONESIAN = "id_301_indonesian"

    private const val TRANSL_TRANSLITERATION_SLUG_PART = "transliteration"

    const val TRANSL_SLUG_DEFAULT = TRANSL_SLUG_EN_SAHIH_INTERNATIONAL
    const val TRANSL_MAX_SELECTION_LIMIT = 6

    private data class PrebuiltSpec(
        val slug: String,
        val manifestPath: String,
        val defaultLangCode: String,
        val defaultLangName: String,
        val defaultDownloadPath: String,
    )

    private val prebuiltSpecs = listOf(
        PrebuiltSpec(
            slug = TRANSL_SLUG_EN_SAHIH_INTERNATIONAL,
            manifestPath = "prebuilt_translations/en_saheeh_v1_1_1/manifest.json",
            defaultLangCode = "en",
            defaultLangName = "English",
            defaultDownloadPath = "prebuilt_translations/en_saheeh_v1_1_1/en_saheeh_v1_1_1.json",
        ),
        PrebuiltSpec(
            slug = TRANSL_SLUG_EN_THE_CLEAR_QURAN,
            manifestPath = "prebuilt_translations/en_the_clear_quran/manifest.json",
            defaultLangCode = "en",
            defaultLangName = "English",
            defaultDownloadPath = "prebuilt_translations/en_the_clear_quran/en_the_clear_quran.json",
        ),
        PrebuiltSpec(
            slug = TRANSL_SLUG_UR_JUNAGARHI,
            manifestPath = "prebuilt_translations/ur_junagarhi/manifest.json",
            defaultLangCode = "ur",
            defaultLangName = "Urdu",
            defaultDownloadPath = "prebuilt_translations/ur_junagarhi/ur_junagarhi.json",
        ),
        PrebuiltSpec(
            slug = TRANSL_SLUG_ID_INDONESIAN,
            manifestPath = "prebuilt_translations/id_indonesian/manifest.json",
            defaultLangCode = "id",
            defaultLangName = "Indonesian",
            defaultDownloadPath = "prebuilt_translations/id_indonesian/id_indonesian.json",
        ),
    )

    fun defaultTranslationSlugs(): HashSet<String> = hashSetOf(TRANSL_SLUG_DEFAULT)

    fun preBuiltTranslBooksInfo(context: Context): List<TranslationBookInfoModel> {
        return prebuiltSpecs.map { createPrebuiltTranslBookInfo(context, it) }
    }

    private fun createPrebuiltTranslBookInfo(
        context: Context,
        spec: PrebuiltSpec,
    ): TranslationBookInfoModel {
        val model = TranslationBookInfoModel(spec.slug)
        val manifest = readPrebuiltManifest(context, spec.manifestPath)
        model.langCode = manifest?.optString("langCode").orEmpty().ifBlank { spec.defaultLangCode }
        model.langName = manifest?.optString("langName").orEmpty().ifBlank { spec.defaultLangName }
        model.bookName = manifest?.optString("book").orEmpty()
        model.authorName = manifest?.optString("author").orEmpty()
        model.displayName = manifest?.optString("displayName").orEmpty().ifBlank { model.bookName }
        model.downloadPath =
            manifest?.optString("downloadPath").orEmpty().ifBlank { spec.defaultDownloadPath }
        model.version = manifest?.optLong("version", 1L) ?: 1L
        model.lastUpdated = model.version
        return model
    }

    private fun readPrebuiltManifest(context: Context, path: String): JSONObject? {
        return runCatching {
            val raw = StringUtils.readInputStream(context.assets.open(path))
            JSONObject(raw)
        }.onFailure {
            Log.saveError(it, "TranslUtils.readPrebuiltManifest")
        }.getOrNull()
    }

    fun getPrebuiltTranslInfoPath(slug: String): String? {
        return prebuiltSpecs.firstOrNull { it.slug == slug }?.manifestPath
    }

    fun getPrebuiltTranslPath(slug: String): String? {
        return prebuiltSpecs.firstOrNull { it.slug == slug }?.defaultDownloadPath
    }

    fun isPrebuilt(slug: String): Boolean {
        return prebuiltSpecs.any { it.slug == slug }
    }

    fun isUrdu(slug: String): Boolean {
        val parts = slug.split('_', '-')
        return parts.firstOrNull() == "ur"
    }

    fun isTransliteration(slug: String): Boolean {
        return slug.contains(TRANSL_TRANSLITERATION_SLUG_PART)
    }

    private fun prepareTranslDirPathForSpecificLangNSlug(
        langCode: String,
        translSlug: String,
    ): String {
        return FileUtils.createPath(langCode, translSlug)
    }

    @JvmStatic
    fun prepareTranslInfoPathForSpecificLangNSlug(langCode: String, translSlug: String): String {
        val path2TranslDir = prepareTranslDirPathForSpecificLangNSlug(langCode, translSlug)
        return FileUtils.createPath(path2TranslDir, TRANSL_INFO_FILE_NAME)
    }

    @JvmStatic
    fun prepareTranslPathForSpecificLangNSlug(
        translId: Int,
        langCode: String,
        translSlug: String,
    ): String {
        val path2TranslDir = prepareTranslDirPathForSpecificLangNSlug(langCode, translSlug)
        val filename =
            StringUtils.formatInvariant(TRANSL_FILE_NAME_FORMAT, translId, langCode, translSlug)
        return FileUtils.createPath(path2TranslDir, filename)
    }

    @Throws(Exception::class)
    fun getTranslInfosAndFilesForMigration(
        fileUtils: FileUtils,
        translDir: File,
    ): List<Pair<TranslationBookInfoModel, File>>? {
        val dirsOfLangCodes = translDir.listFiles()
        if (dirsOfLangCodes.isNullOrEmpty()) {
            Log.d("Nothing was found, deleting root translation directory: ${translDir.name}")
            fileUtils.deleteRecursively(translDir)
            return null
        }

        val translInfosAndFiles = ArrayList<Pair<TranslationBookInfoModel, File>>()

        for (langCodeDir in dirsOfLangCodes) {
            val translFiles = langCodeDir.listFiles()
            if (translFiles.isNullOrEmpty()) {
                Log.d("Deleting language directory with its contents: ${langCodeDir.name}")
                fileUtils.deleteRecursively(langCodeDir)
                continue
            }

            for (singleTranslDir in translFiles) {
                if (!singleTranslDir.isDirectory) {
                    fileUtils.deleteRecursively(singleTranslDir)
                    continue
                }

                try {
                    val infoJSONFile = File(singleTranslDir, TRANSL_INFO_FILE_NAME)
                    val pair = readTranslInfoFromJSONFile(fileUtils, infoJSONFile)
                    if (pair == null) {
                        Logger.print(
                            "Deleting translation directory with its manifest and data files: ${singleTranslDir.name}",
                        )
                        fileUtils.deleteRecursively(singleTranslDir)
                        continue
                    }

                    translInfosAndFiles.add(pair)
                } catch (e: Exception) {
                    Logger.print(
                        "Error occurred, deleting translation directory with its manifest and data files dire: ${singleTranslDir.name}",
                    )
                    fileUtils.deleteRecursively(singleTranslDir)
                    e.printStackTrace()
                }
            }
        }

        return translInfosAndFiles
    }

    private fun readTranslInfoFromJSONFile(
        fileUtils: FileUtils,
        infoJSONFile: File,
    ): Pair<TranslationBookInfoModel, File>? {
        if (!infoJSONFile.isFile) return null

        val json = fileUtils.readText(infoJSONFile)
        val jsonObject = JSONObject(json)

        val slug = jsonObject.optString("slug", "")
        if (isPrebuilt(slug)) return null

        val id = jsonObject.optInt("id")
        val langCode = jsonObject.optString("langCode", jsonObject.optString("lang-code", ""))
        val translFile = fileUtils.getSingleTranslationFile(id, langCode, slug)

        if (!translFile.exists() || translFile.length() == 0L) {
            Log.d("Translation file does not exist or is empty.")
            return null
        }

        val bookInfo = TranslationBookInfoModel(slug)
        bookInfo.langCode = langCode
        bookInfo.bookName = jsonObject.optString("book", "")
        bookInfo.authorName = jsonObject.optString("author", "")
        bookInfo.langName = jsonObject.optString("langName", jsonObject.optString("lang-name", ""))
        bookInfo.displayName =
            jsonObject.optString("displayName", jsonObject.optString("display-name", ""))
        bookInfo.version = jsonObject.optLong("version", 1L)
        bookInfo.lastUpdated = bookInfo.version
        bookInfo.downloadPath = jsonObject.optString("downloadPath", "")
        return Pair(bookInfo, translFile)
    }

    fun resolveSelectionChange(
        ctx: Context,
        slugSet: MutableSet<String>,
        model: TranslModel,
        isSelected: Boolean,
    ): Boolean {
        val slug = model.bookInfo.slug
        if (isSelected) {
            if (slugSet.size >= TRANSL_MAX_SELECTION_LIMIT) {
                val msg =
                    ctx.getString(R.string.strMsgTranslSelectionLimit, TRANSL_MAX_SELECTION_LIMIT)
                val btn = ctx.getString(R.string.strLabelGotIt)
                MessageUtils.popMessage(ctx, ctx.getString(R.string.strTitleInfo), msg, btn, null)
                return false
            }
            slugSet.add(slug)
        } else {
            slugSet.remove(slug)
        }
        return true
    }
}
