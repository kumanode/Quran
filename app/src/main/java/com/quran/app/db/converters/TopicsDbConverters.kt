package com.quran.app.db.converters

import androidx.room.TypeConverter
import com.quran.app.db.entities.topics.RelationshipType
import com.quran.app.db.entities.topics.TopicFlags
import com.quran.app.db.entities.quran.MushafLineType
import com.quran.app.db.entities.quran.NavigationType
import com.quran.app.db.entities.quran.RevelationType

class TopicsDbConverters {
    @TypeConverter
    fun fromRelationshipType(value: RelationshipType?): String? = value?.dbValue

    @TypeConverter
    fun toRelationshipType(value: String?): RelationshipType? =
        value?.let { RelationshipType.fromDbValue(it) }

    @TypeConverter
    fun fromTopicFlags(value: TopicFlags?): Int? = value?.dbValue

    @TypeConverter
    fun toTopicFlags(value: Int?): TopicFlags? =
        value?.let { TopicFlags.fromDbValue(it) }
}
