package com.example.punchandroidtest.data.remote.dto


import com.example.punchandroidtest.data.db.dto.MarsEntity
import com.example.punchandroidtest.domain.model.Mars
import com.google.gson.annotations.SerializedName

data class MarsDto(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("img_src")
    val imgSrc: String = "",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("type")
    val type: String = ""
)

fun MarsDto.toMars() : Mars {
    return Mars(
        id = id,
        imageSource = imgSrc,
        price = price,
        type = type
    )
}

fun MarsDto.toMarsEntity(): MarsEntity {
    return MarsEntity(
        id = id,
        imageSource = imgSrc,
        price = price,
        type = type
    )
}