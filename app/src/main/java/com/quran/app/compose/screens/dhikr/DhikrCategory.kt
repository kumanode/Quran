package com.quran.app.compose.screens.dhikr

import androidx.annotation.StringRes
import com.quran.app.R

enum class DhikrCategory(
    val id: String,
    @get:StringRes val titleRes: Int
) {
    ALL("all", R.string.dhikr_cat_all),
    MORNING("morning", R.string.dhikr_cat_morning),
    EVENING("evening", R.string.dhikr_cat_evening),
    POST_PRAYER("post_prayer", R.string.dhikr_cat_post_prayer),
    TAHLIL("tahlil", R.string.dhikr_cat_tahlil),
    DAILY("daily", R.string.dhikr_cat_daily),
    QURANIC("quranic", R.string.dhikr_cat_quranic)
}
