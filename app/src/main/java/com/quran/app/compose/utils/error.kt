package com.quran.app.compose.utils

sealed interface DataLoadError {
    object NoConnection : DataLoadError
    object NoData : DataLoadError
    object Failed : DataLoadError
}
