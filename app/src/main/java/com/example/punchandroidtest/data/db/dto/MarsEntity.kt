package com.example.punchandroidtest.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.punchandroidtest.domain.model.Mars

@Entity(tableName = "mars")
data class MarsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "image_source")
    val imageSource: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "type")
    val type: String
)

fun MarsEntity.toMars(): Mars {
    return Mars(
        id = id,
        imageSource = imageSource,
        price = price,
        type = type
    )
}

fun Mars.toMarsEntity() : MarsEntity {
    return MarsEntity(
        id = id,
        imageSource = imageSource,
        price = price,
        type = type
    )
}