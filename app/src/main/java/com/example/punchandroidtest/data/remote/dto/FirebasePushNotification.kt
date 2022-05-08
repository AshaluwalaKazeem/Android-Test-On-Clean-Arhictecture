package com.example.punchandroidtest.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FirebasePushNotification(
    @SerializedName("body")
    val body: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("title")
    val title: String = ""
)