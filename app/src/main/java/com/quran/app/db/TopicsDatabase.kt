package com.quran.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quran.app.db.converters.QuranConverters
import com.quran.app.db.converters.TopicsDbConverters
import com.quran.app.db.dao.TopicsDao
import com.quran.app.db.entities.topics.RelationshipEntity
import com.quran.app.db.entities.topics.TopicAyahEntity
import com.quran.app.db.entities.topics.TopicEntity
import com.quran.app.db.entities.topics.TopicLocalizationEntity

@Database(
    entities = [
        TopicEntity::class,
        TopicLocalizationEntity::class,
        TopicAyahEntity::class,
        RelationshipEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(QuranConverters::class, TopicsDbConverters::class)
abstract class TopicsDatabase : RoomDatabase() {
    abstract fun topicsDao(): TopicsDao
}
