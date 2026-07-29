package com.quran.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quran.app.db.converters.QuranConverters
import com.quran.app.db.dao.ArabicSearchDao
import com.quran.app.db.dao.AyahDao
import com.quran.app.db.dao.AyahWordDao
import com.quran.app.db.dao.ExtrasDao
import com.quran.app.db.dao.MushafDao
import com.quran.app.db.dao.NavigationDao
import com.quran.app.db.dao.SurahDao
import com.quran.app.db.dao.SurahSearchDao
import com.quran.app.db.entities.extras.MutashabihatPhraseAyahEntity
import com.quran.app.db.entities.extras.MutashabihatPhraseEntity
import com.quran.app.db.entities.extras.SimilarVerseEntity
import com.quran.app.db.entities.quran.ArabicSearchFtsEntity
import com.quran.app.db.entities.quran.AyahEntity
import com.quran.app.db.entities.quran.AyahWordEntity
import com.quran.app.db.entities.quran.MushafEntity
import com.quran.app.db.entities.quran.MushafMapEntity
import com.quran.app.db.entities.quran.NavigationRangeEntity
import com.quran.app.db.entities.quran.ScriptEntity
import com.quran.app.db.entities.quran.SurahAliasFtsEntity
import com.quran.app.db.entities.quran.SurahEntity
import com.quran.app.db.entities.quran.SurahLocalizationEntity
import com.quran.app.db.entities.quran.SurahSearchAliasEntity

@Database(
    entities = [
        SurahEntity::class,
        SurahLocalizationEntity::class,
        SurahSearchAliasEntity::class,
        SurahAliasFtsEntity::class,
        AyahEntity::class,
        ScriptEntity::class,
        AyahWordEntity::class,
        NavigationRangeEntity::class,
        MushafEntity::class,
        MushafMapEntity::class,
        ArabicSearchFtsEntity::class,
        SimilarVerseEntity::class,
        MutashabihatPhraseEntity::class,
        MutashabihatPhraseAyahEntity::class,
    ],
    version = 11,
    exportSchema = false
)
@TypeConverters(QuranConverters::class)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun arabicSearchDao(): ArabicSearchDao
    abstract fun surahDao(): SurahDao
    abstract fun surahSearchDao(): SurahSearchDao
    abstract fun ayahDao(): AyahDao
    abstract fun ayahWordDao(): AyahWordDao
    abstract fun navigationDao(): NavigationDao
    abstract fun mushafDao(): MushafDao
    abstract fun extrasDao(): ExtrasDao
}
