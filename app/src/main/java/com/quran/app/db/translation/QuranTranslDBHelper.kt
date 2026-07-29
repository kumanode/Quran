/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 6/6/2022.
 * All rights reserved.
 */

package com.quran.app.db.translation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction
import com.quran.app.api.models.translation.TranslationBookInfoModel
import com.quran.app.db.translation.QuranTranslContract.QuranTranslEntry.COL_CHAPTER_NO
import com.quran.app.db.translation.QuranTranslContract.QuranTranslEntry.COL_FOOTNOTES
import com.quran.app.db.translation.QuranTranslContract.QuranTranslEntry.COL_TEXT
import com.quran.app.db.translation.QuranTranslContract.QuranTranslEntry.COL_VERSE_NO
import com.quran.app.db.translation.QuranTranslContract.QuranTranslEntry.TABLE_NAME
import com.quran.app.db.translation.QuranTranslContract.QuranTranslEntry._ID
import com.quran.app.db.translation.QuranTranslInfoContract.QuranTranslInfoEntry
import com.quran.app.utils.Log
import com.quran.app.utils.quran.QuranConstants
import com.quran.app.utils.reader.TranslUtils
import com.quran.app.utils.univ.FileUtils
import com.quran.app.utils.univ.StringUtils
import org.json.JSONObject
import java.io.File

class QuranTranslDBHelper(private val context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {
    companion object {
        private const val DB_NAME = "QuranTranslation.db"
        const val DB_VERSION = 1
        private const val BUSY_TIMEOUT_MS = 5000
        private const val LEGACY_VERSION_THRESHOLD = 1_000_000L

        /**
         * Escapes tables name as it may contain special characters.
         * For example, as in "en_sahih-international"
         */
        @JvmStatic
        fun escapeTableName(tableName: String): String {
            return "`$tableName`"
        }

        @JvmStatic
        fun translationsOrderBy(): String {
            return "$COL_CHAPTER_NO ASC, $COL_VERSE_NO ASC"
        }
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)

        if (!db.isReadOnly) {
            db.enableWriteAheadLogging()
        }

        db.rawQuery("PRAGMA busy_timeout=$BUSY_TIMEOUT_MS", null).close()
    }

    override fun onUpgrade(DB: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        DB.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(DB)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        if (!db.isReadOnly) {
            runCatching {
                syncPrebuiltTranslationsIfNeeded(db)
            }.onFailure {
                Log.saveError(it, "QuranTranslDBHelper.onOpen.syncPrebuiltTranslationsIfNeeded")
            }
        }
    }

    override fun onCreate(DB: SQLiteDatabase) {
        createTranslInfoTable(DB)
        DB.transaction {
            try {
                for (bookInfo in TranslUtils.preBuiltTranslBooksInfo(context)) {
                    val prebuiltTranslPath =
                        TranslUtils.getPrebuiltTranslPath(bookInfo.slug) ?: continue

                    val translStrData = StringUtils.readInputStream(
                        context.assets.open(prebuiltTranslPath)
                    )

                    storeTranslation(bookInfo, translStrData, this)
                }

                migrateFileBasedTranslsToDatabase(context, this)
            } catch (e: Exception) {
                Log.saveError(e, "QuranTranslDBHelper.onCreate")
                e.printStackTrace()
            }
        }
    }

    /**
     * The DB instance is already running in a transaction in QuranTranslDBHelper for storing built-in translations,
     * so do not close or do anything else except storing translation data.
     */
    private fun migrateFileBasedTranslsToDatabase(context: Context, DB: SQLiteDatabase) {
        val fileUtils = FileUtils.newInstance(context)
        val translDir = File(fileUtils.appFilesDirectory, TranslUtils.DIR_NAME)
        if (!translDir.exists()) return

        try {
            TranslUtils.getTranslInfosAndFilesForMigration(fileUtils, translDir)?.let {
                for (pair in it) {
                    storeTranslation(pair.first, pair.second.readText(), DB)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            translDir.deleteRecursively()
            Log.d(
                "Migration finished anyhow, deleting root translation directory: " + translDir.name
            )
        }
    }

    private fun createTranslInfoTable(DB: SQLiteDatabase) {
        DB.execSQL(
            "CREATE TABLE ${QuranTranslInfoEntry.TABLE_NAME} (" +
                    "${QuranTranslInfoEntry.COL_SLUG} TEXT PRIMARY KEY," +
                    "${QuranTranslInfoEntry.COL_LANG_CODE} TEXT," +
                    "${QuranTranslInfoEntry.COL_LANG_NAME} TEXT," +
                    "${QuranTranslInfoEntry.COL_BOOK_NAME} TEXT," +
                    "${QuranTranslInfoEntry.COL_AUTHOR_NAME} TEXT," +
                    "${QuranTranslInfoEntry.COL_DISPLAY_NAME} TEXT," +
                    "${QuranTranslInfoEntry.COL_LAST_UPDATED} LONG," +
                    "${QuranTranslInfoEntry.COL_DOWNLOAD_PATH} TEXT," +
                    "${QuranTranslInfoEntry.COL_IS_PREMIUM} BOOLEAN)"
        )
    }

    private fun makeVerseKey(chapterNo: Int, verseNo: Int): String {
        return "$chapterNo:$verseNo"
    }

    private fun createTranslTable(DB: SQLiteDatabase, bookInfo: TranslationBookInfoModel) {
        DB.execSQL(
            "CREATE TABLE IF NOT EXISTS ${escapeTableName(bookInfo.slug)} (" +
                    "$_ID TEXT PRIMARY KEY," +
                    "$COL_CHAPTER_NO INTEGER," +
                    "$COL_VERSE_NO INTEGER," +
                    "$COL_TEXT TEXT," +
                    "$COL_FOOTNOTES TEXT)"
        )
    }

    private fun readAndInsertChapters(
        DB: SQLiteDatabase,
        bookInfo: TranslationBookInfoModel,
        root: JSONObject
    ) {
        val chapters = root.optJSONArray(QuranConstants.KEY_CHAPTER_LIST) ?: return
        for (i in 0 until chapters.length()) {
            val chapterObj = chapters.optJSONObject(i) ?: continue
            readAndInsertSingleChapter(DB, bookInfo, chapterObj)
        }
    }

    private fun readAndInsertSingleChapter(
        DB: SQLiteDatabase,
        bookInfo: TranslationBookInfoModel,
        chapterObj: JSONObject
    ) {
        val chapterNo = chapterObj.optInt(QuranConstants.KEY_NUMBER, -1)
        val verses = chapterObj.optJSONArray(QuranConstants.KEY_VERSE_LIST) ?: return
        for (i in 0 until verses.length()) {
            val verseObj = verses.optJSONObject(i) ?: continue
            val footnotes =
                verseObj.optJSONArray(QuranConstants.KEY_FOOTNOTE_LIST)?.toString() ?: "[]"
            insertTranslationQuery(
                DB,
                bookInfo.slug,
                chapterNo,
                verseObj.optInt(QuranConstants.KEY_NUMBER, -1),
                verseObj.optString(QuranConstants.KEY_TRANSLATION_TEXT, ""),
                footnotes
            )
        }
    }

    private fun insertTranslationQuery(
        DB: SQLiteDatabase,
        tableName: String,
        chapterNo: Int,
        verseNo: Int,
        text: String,
        footnotes: String?
    ) {
        val values = ContentValues().apply {
            put(_ID, makeVerseKey(chapterNo, verseNo))
            put(COL_CHAPTER_NO, chapterNo)
            put(COL_VERSE_NO, verseNo)
            put(COL_TEXT, text)
            put(COL_FOOTNOTES, footnotes)
        }
        DB.insert(escapeTableName(tableName), null, values)
    }

    private fun storeTranslationInfo(bookInfo: TranslationBookInfoModel, DB: SQLiteDatabase) {
        val storedVersion = if (bookInfo.version > 0) {
            bookInfo.version
        } else {
            1L
        }

        val values = ContentValues().apply {
            put(QuranTranslInfoEntry.COL_SLUG, bookInfo.slug)
            put(QuranTranslInfoEntry.COL_LANG_CODE, bookInfo.langCode)
            put(QuranTranslInfoEntry.COL_LANG_NAME, bookInfo.langName)
            put(QuranTranslInfoEntry.COL_BOOK_NAME, bookInfo.bookName)
            put(QuranTranslInfoEntry.COL_AUTHOR_NAME, bookInfo.authorName)
            put(QuranTranslInfoEntry.COL_DISPLAY_NAME, bookInfo.displayName)
            put(QuranTranslInfoEntry.COL_LAST_UPDATED, storedVersion)
            put(QuranTranslInfoEntry.COL_DOWNLOAD_PATH, bookInfo.downloadPath)
        }

        DB.insertWithOnConflict(
            QuranTranslInfoEntry.TABLE_NAME,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE,
        )
    }

    fun storeTranslation(
        bookInfo: TranslationBookInfoModel,
        translData: String,
        DB: SQLiteDatabase?
    ) {
        fun runStore(db: SQLiteDatabase) {
            db.execSQL("DROP TABLE IF EXISTS ${escapeTableName(bookInfo.slug)}")
            storeTranslationInfo(bookInfo, db)
            createTranslTable(db, bookInfo)

            try {
                val root = JSONObject(translData)
                readAndInsertChapters(db, bookInfo, root)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (DB == null) {
            writableDatabase.transaction {
                runStore(this)
            }
        } else {
            runStore(DB)
        }
    }

    fun storeTranslation(bookInfo: TranslationBookInfoModel, translData: String) {
        storeTranslation(bookInfo, translData, writableDatabase)
    }

    private fun syncPrebuiltTranslationsIfNeeded(db: SQLiteDatabase) {
        val prebuiltInfos = TranslUtils.preBuiltTranslBooksInfo(context)
        if (prebuiltInfos.isEmpty()) return

        db.transaction {
            for (bookInfo in prebuiltInfos) {
                val requiredVersion = if (bookInfo.version > 0) bookInfo.version else 1L
                val rawCurrentVersion = getStoredVersion(this, bookInfo.slug)
                val currentVersion = normalizeStoredVersion(rawCurrentVersion)

                // Backfill legacy timestamp-based values so future version checks work.
                if (rawCurrentVersion != null && rawCurrentVersion != currentVersion) {
                    updateStoredVersion(this, bookInfo.slug, currentVersion)
                }

                if (currentVersion >= requiredVersion) continue

                val prebuiltTranslPath =
                    TranslUtils.getPrebuiltTranslPath(bookInfo.slug) ?: continue
                val translStrData = StringUtils.readInputStream(
                    context.assets.open(prebuiltTranslPath)
                )
                storeTranslation(bookInfo, translStrData, this)
            }
        }
    }

    private fun getStoredVersion(db: SQLiteDatabase, slug: String): Long? {
        db.query(
            true,
            QuranTranslInfoEntry.TABLE_NAME,
            arrayOf(QuranTranslInfoEntry.COL_LAST_UPDATED),
            "${QuranTranslInfoEntry.COL_SLUG}=?",
            arrayOf(slug),
            null,
            null,
            null,
            "1"
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return cursor.getLong(cursor.getColumnIndexOrThrow(QuranTranslInfoEntry.COL_LAST_UPDATED))
        }
    }

    private fun updateStoredVersion(db: SQLiteDatabase, slug: String, version: Long) {
        val values = ContentValues().apply {
            put(QuranTranslInfoEntry.COL_LAST_UPDATED, version)
        }
        db.update(
            QuranTranslInfoEntry.TABLE_NAME,
            values,
            "${QuranTranslInfoEntry.COL_SLUG}=?",
            arrayOf(slug),
        )
    }

    private fun normalizeStoredVersion(raw: Long?): Long {
        if (raw == null || raw <= 0L) return 1L
        if (raw > LEGACY_VERSION_THRESHOLD) return 1L
        return raw
    }
}
