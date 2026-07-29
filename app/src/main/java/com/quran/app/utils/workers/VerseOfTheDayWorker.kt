package com.quran.app.utils.workers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.quran.app.R
import com.quran.app.activities.ActivityReader
import com.quran.app.components.reader.ChapterVersePair
import com.quran.app.compose.utils.formatStringResource
import com.quran.app.compose.utils.preferences.ReaderPreferences
import com.quran.app.compose.utils.preferences.VersePreferences
import com.quran.app.db.DatabaseProvider
import com.quran.app.db.relations.VerseWithDetails
import com.quran.app.utils.app.NotificationUtils
import com.quran.app.utils.reader.ReaderIntentData
import com.quran.app.utils.reader.ReaderLaunchParams
import com.quran.app.utils.reader.TranslUtils
import com.quran.app.utils.reader.factory.QuranTranslationFactory
import com.quran.app.utils.univ.Codes
import com.quran.app.utils.univ.StringUtils
import com.quran.app.utils.verse.VerseUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VerseOfTheDayWorker constructor(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        if (!VersePreferences.getVOTDReminderEnabled()) {
            return@withContext Result.success()
        }

        val repository = DatabaseProvider.getQuranRepository(applicationContext)
        val votd = VerseUtils.getVOTD(applicationContext, repository)
            ?: return@withContext Result.failure()

        sendNotification(votd)

        return@withContext Result.success()
    }

    private suspend fun sendNotification(votd: VerseWithDetails) {
        val context = applicationContext

        var slugs = ReaderPreferences.getTranslations()
        if (slugs.isEmpty()) {
            slugs = TranslUtils.defaultTranslationSlugs()
        }

        val factory = QuranTranslationFactory(context)
        val translations = factory.getTranslationsSingleVerse(slugs, votd.chapterNo, votd.verseNo)

        if (translations.isEmpty()) {
            return
        }

        val manager = ContextCompat.getSystemService(
            context, NotificationManager::class.java
        ) ?: return

        val translationText = StringUtils.removeHTML(translations[0].text, false)

        val verseReference = formatStringResource(
            context,
            R.string.strLabelVerseSerialWithChapter,
            votd.chapter.getCurrentName(),
            votd.chapterNo,
            votd.verseNo
        )

        val readerIntent = ReaderLaunchParams(
            data = ReaderIntentData.FullChapter(
                votd.chapterNo,
                ChapterVersePair(votd.chapterNo, votd.verseNo),
            ),
            slugs = slugs,
        ).toIntent().apply {
            setClass(context, ActivityReader::class.java)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            Codes.NOTIF_ID_VOTD,
            readerIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )


        val notification = NotificationCompat
            .Builder(applicationContext, NotificationUtils.CHANNEL_ID_VOTD)
            .setContentTitle(context.getString(R.string.strTitleVOTD))
            .setContentText(translationText)
            .setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText(translationText)
            )
            .setSubText(verseReference)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        manager.notify(Codes.NOTIF_ID_VOTD, notification)
    }
}