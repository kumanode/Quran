package com.quran.app.api

import com.quran.app.api.models.tafsir.ChapterInfoApiResponse
import com.quran.app.api.models.tafsir.TafsirResponseModel
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlfaazPlusApi {
    @GET("/quran/chapters/{chapter}/info")
    suspend fun getChapterInfo(
        @Path("chapter") chapterNo: Int,
        @Query("language") language: String,
        @Query("id") id: Int?,
    ): ChapterInfoApiResponse

    @GET("/quran/tafsirs")
    suspend fun getAvailableTafsirs(): ResponseBody

    @GET("/quran/tafsirs/by_verse")
    suspend fun getTafsirsByVerse(
        @Query("keys") tafsirKeys: String,
        @Query("verse_key") verseKey: String,
    ): TafsirResponseModel

    @GET("/quran/tafsirs/by_surah")
    suspend fun getTafsirsByChapter(
        @Query("key") tafsirKey: String,
        @Query("surahs") surahRange: String,
    ): TafsirResponseModel
}
