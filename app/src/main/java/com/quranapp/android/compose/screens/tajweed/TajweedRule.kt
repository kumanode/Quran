package com.quranapp.android.compose.screens.tajweed

import androidx.annotation.StringRes

data class TajweedRule(
    val id: String,
    @StringRes val titleRes: Int,
    @StringRes val descRes: Int,
    @StringRes val exampleRes: Int? = null,
    val subRules: List<TajweedRule> = emptyList()
)
