package com.quran.app.api.models.tafsir

import kotlinx.serialization.Serializable

@Serializable
data class AvailableTafsirsModel(
    val version: String,
    val tafsirs: Map<String, List<TafsirInfoModel>>
)
