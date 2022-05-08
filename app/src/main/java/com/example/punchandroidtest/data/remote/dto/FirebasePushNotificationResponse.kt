package com.example.punchandroidtest.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FirebasePushNotificationResponse(
    @SerializedName("message_id")
    val messageId: Long? = null
)