package com.quran.app.api.models.recitation2

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableRecitationTranslationsModel(
    @SerialName("reciters") val reciters: List<RecitationTranslationModel>
)
