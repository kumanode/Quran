package com.quran.app.compose.screens.dhikr

import androidx.annotation.StringRes

data class DhikrDuaItem(
    val id: String,
    val category: DhikrCategory,
    @get:StringRes val titleRes: Int,
    val arabicText: String,
    @get:StringRes val latinRes: Int? = null,
    @get:StringRes val translationRes: Int,
    @get:StringRes val virtueRes: Int? = null,
    val targetCount: Int = 1,
    val surahNo: Int? = null,
    val verseNo: Int? = null,
    val toVerseNo: Int? = null,
    val audioUrl: String? = null
)
