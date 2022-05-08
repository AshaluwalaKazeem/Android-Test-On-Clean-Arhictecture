package com.example.punchandroidtest.data.remote.dto


import com.example.punchandroidtest.common.Constants
import com.example.punchandroidtest.domain.model.PushNotification
import com.google.gson.annotations.SerializedName

data class FirebasePushNotificationDto(
    @SerializedName("data")
    val data: FirebasePushNotificationData = FirebasePushNotificationData(),
    @SerializedName("notification")
    val notification: FirebasePushNotification = FirebasePushNotification(),
    @SerializedName("to")
    val to: String = ""
)

fun PushNotification.toFirebasePushNotificationDto(topic : String) : FirebasePushNotificationDto {
    return FirebasePushNotificationDto(
        data = FirebasePushNotificationData(
            body = body,
            image = image,
            title = title
        ),
        notification = FirebasePushNotification(
            body = body,
            image = image,
            title = title
        ),
        to = "/topics/$topic"
    )
}