package com.quran.app.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.quran.app.db.entities.quran.AyahEntity
import com.quran.app.db.entities.quran.AyahWordEntity

data class AyahWithWords(
    @Embedded
    val ayah: AyahEntity,

    @Relation(
        parentColumn = "ayah_id",
        entityColumn = "ayah_id"
    )
    val words: List<AyahWordEntity>
)