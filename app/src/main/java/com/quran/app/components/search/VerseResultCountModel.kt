package com.quran.app.components.search

import com.quran.app.api.models.translation.TranslationBookInfoModel

class VerseResultCountModel(val bookInfo: TranslationBookInfoModel?) : SearchResultModelBase() {
    @JvmField
    var resultCount = 0
}
