package com.quran.app.db.relations

import com.quran.app.db.entities.quran.NavigationType

data class NavigationUnitRange(
    val surah: SurahWithLocalizations,
    val startAyah: Int,
    val endAyah: Int
)

data class NavigationUnit(
    val type: NavigationType,
    val unitNo: Int,
    val ranges: List<NavigationUnitRange>
)