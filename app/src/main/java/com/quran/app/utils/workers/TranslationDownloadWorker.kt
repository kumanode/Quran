package com.quran.app.utils.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.quran.app.R
import com.quran.app.activities.ActivitySettings
import com.quran.app.api.RetrofitInstance
import com.quran.app.api.models.translation.TranslationBookInfoModel
import com.quran.app.compose.navigation.SettingRoutes
import com.quran.app.search.SearchIndexScheduler
import com.quran.app.utils.Logger
import com.quran.app.utils.app.AppActions
import com.quran.app.utils.app.NotificationUtils
import com.quran.app.utils.app.NotificationUtils.createForegroundInfoFallback
import com.quran.app.utils.reader.factory.QuranTranslationFactory
import com.quran.app.utils.sharedPrefs.SPAppActions.removeFromPendingAction
import com.quran.app.utils.univ.Keys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File

class TranslationDownloadWorker(
    val ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun getForegroundInfo(): ForegroundInfo {
        val bookInfoJson = inputData.getString("bookInfo")
            ?: return createForegroundInfoFallback(ctx)
        val bookInfo = Json.decodeFromString<TranslationBookInfoModel>(bookInfoJson)


        return createForegroundInfo(bookInfo, 0)
    }

    override suspend fun doWork(): Result {
        val bookInfoJson = inputData.getString("bookInfo") ?: return Result.failure()
        val bookInfo = Json.decodeFromString<TranslationBookInfoModel>(bookInfoJson)

        setForeground(createForegroundInfo(bookInfo, 0))

        return try {
            downloadFile(bookInfo)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun downloadFile(
        bookInfo: TranslationBookInfoModel
    ) = withContext(Dispatchers.IO) {
        val tmpFile = File.createTempFile(
            bookInfo.slug,
            ".json",
            ctx.cacheDir
        )
        try {
            val response = RetrofitInstance.github.getTranslation(bookInfo.downloadPath)

            if (!response.isSuccessful) throw Exception("HTTP ${response.code()}")
            val body = response.body() ?: throw Exception("Empty body")
            val totalBytes = body.contentLength()
            val byteStream = body.byteStream()

            byteStream.use { inS ->
                tmpFile.outputStream().buffered().use { outS ->


                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var downloaded = 0L

                    while (true) {
                        ensureActive()

                        if (isStopped) break

                        val bytes = inS.read(buffer)

                        if (bytes <= 0) break

                        outS.write(buffer, 0, bytes)
                        downloaded += bytes

                        val progress =
                            if (totalBytes > 0) ((downloaded * 100) / totalBytes).toInt() else null
                        setProgressAsync(workDataOf("progress" to progress))
                        setForeground(createForegroundInfo(bookInfo, progress))
                    }

                    outS.flush()
                }
            }

            QuranTranslationFactory(ctx).use {
                it.dbHelper.storeTranslation(bookInfo, tmpFile.readText())
            }

            SearchIndexScheduler.enqueueSlug(ctx.applicationContext, bookInfo.slug)

            removeFromPendingAction(ctx, AppActions.APP_ACTION_TRANSL_UPDATE, bookInfo.slug)
        } finally {
            tmpFile.delete()
        }
    }

    private fun createForegroundInfo(
        bookInfo: TranslationBookInfoModel,
        progress: Int?
    ): ForegroundInfo {
        val channelId = NotificationUtils.CHANNEL_ID_DOWNLOADS
        val builder = NotificationCompat.Builder(ctx, channelId).apply {
            setAutoCancel(false)
            setOngoing(true)
            setShowWhen(false)
            setSmallIcon(R.mipmap.ic_launcher_foreground)
            setContentTitle(ctx.getString(R.string.textDownloading))
            setContentText(bookInfo.bookName)
            setCategory(NotificationCompat.CATEGORY_PROGRESS)
            setProgress(100, progress ?: 0, progress == null)
        }

        var flag = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = flag or PendingIntent.FLAG_IMMUTABLE
        }

        val activityIntent = Intent(ctx, ActivitySettings::class.java).apply {
            putExtra(
                Keys.NAV_DESTINATION,
                SettingRoutes.TRANSLATIONS_DOWNLOAD
            )
        }
        val pendingIntent = PendingIntent.getActivity(
            ctx,
            bookInfo.slug.hashCode(),
            activityIntent,
            flag
        )
        builder.setContentIntent(pendingIntent)

        val cancelIntent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        builder.addAction(
            R.drawable.dr_icon_close,
            ctx.getString(R.string.strLabelCancel),
            cancelIntent
        )

        val notificationId = bookInfo.slug.hashCode()
        val notification = builder.build()

        return ForegroundInfo(
            notificationId,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        )
    }
}
