package com.quran.app.db.relations

import android.content.Context
import com.quran.app.components.quran.subcomponents.Translation
import com.quran.app.db.entities.quran.AyahEntity
import com.quran.app.db.entities.quran.AyahWordEntity
import com.quran.app.db.interfaces.SurahMethods
import com.quran.app.utils.verse.VerseUtils

data class VerseWithDetails(
    val words: List<AyahWordEntity>,
    val pageNo: Int,
    val verse: AyahEntity,
    val chapter: SurahWithLocalizations
) : SurahMethods by chapter {
    val id get() = verse.ayahId
    val chapterNo get() = verse.surahNo
    val verseNo get() = verse.ayahNo

    var translations: List<Translation> = ArrayList()
    var includeChapterNameInSerial = false

    fun getTranslationCount() = translations.size

    fun isVOTD(ctx: Context) = VerseUtils.isVOTD(chapterNo, verseNo)

    fun isIdealForVOTD(): Boolean {
        val arabicText = words.joinToString(" ") { it.text }
        return arabicText.length in 6..300
    }

    override fun toString(): String {
        return "VERSE ($id) -  $chapterNo:$verseNo"
    }
}
