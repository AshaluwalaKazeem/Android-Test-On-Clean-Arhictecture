package com.example.punchandroidtest.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FirebasePushNotificationData(
    @SerializedName("body")
    val body: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("title")
    val title: String = ""
)