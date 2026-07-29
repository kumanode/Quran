package com.quran.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quran.app.db.converters.QuranConverters
import com.quran.app.db.dao.AtlasWordShapeDao
import com.quran.app.db.dao.WbwDao
import com.quran.app.db.entities.atlas.AtlasBundleEntity
import com.quran.app.db.entities.atlas.AtlasWordShapeEntity
import com.quran.app.db.entities.wbw.WbwAudioTimingEntity
import com.quran.app.db.entities.wbw.WbwWordEntity

@Database(
    entities = [
        WbwWordEntity::class,
        WbwAudioTimingEntity::class,
        AtlasBundleEntity::class,
        AtlasWordShapeEntity::class,
    ],
    version = 4,
    exportSchema = true
)
@TypeConverters(QuranConverters::class)
abstract class ExternalQuranDatabase : RoomDatabase() {
    abstract fun wbwDao(): WbwDao
    abstract fun atlasWordShapeDao(): AtlasWordShapeDao
}
