package com.quran.app.utils.chapterInfo

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.quran.app.utils.quran.QuranMeta
import com.quran.app.utils.univ.MessageUtils

class ChapterInfoJSInterface(
    private val context: Context,
    private val verseCount: Int,
    private val onOpenReference: (chapterNo: Int, fromVerse: Int, toVerse: Int) -> Unit,
) {
    @JavascriptInterface
    fun openReference(chapterNo: Int, fromVerse: Int, toVerse: Int) {
        if (!QuranMeta.isChapterValid(chapterNo)
            || fromVerse < 1 || toVerse < 1
            || fromVerse > toVerse
            || fromVerse > verseCount
            || toVerse > verseCount
        ) {
            MessageUtils.showRemovableToast(context, "Could not open references", Toast.LENGTH_LONG)
            return
        }

        onOpenReference(chapterNo, fromVerse, toVerse)
    }
}
