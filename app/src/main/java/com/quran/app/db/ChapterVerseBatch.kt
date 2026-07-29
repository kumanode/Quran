package com.quran.app.db

import com.quran.app.db.entities.quran.AyahEntity
import com.quran.app.db.entities.quran.AyahWordEntity
import com.quran.app.db.relations.SurahWithLocalizations

data class ChapterVerseBatch(
    val surah: SurahWithLocalizations,
    val ayahByVerseNo: Map<Int, AyahEntity>,
    val wordsByVerseNo: Map<Int, List<AyahWordEntity>>,
    val pageByVerseNo: Map<Int, Int>,
)
