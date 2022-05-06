package com.example.punchandroidtest.data.db.dto

import androidx.compose.runtime.mutableStateOf
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
        id = mutableStateOf(id),
        imageSource = mutableStateOf(imageSource),
        price = mutableStateOf(price),
        type = mutableStateOf(type)
    )
}

fun Mars.toMarsEntity() : MarsEntity {
    return MarsEntity(
        id = id.value,
        imageSource = imageSource.value,
        price = price.value,
        type = type.value
    )
}