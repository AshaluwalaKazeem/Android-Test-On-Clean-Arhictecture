package com.example.punchandroidtest.data.remote

import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationDto
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationResponse
import com.example.punchandroidtest.data.remote.dto.MarsDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FirebaseApi {

    @POST("send")
    suspend fun sendPushNotification(
        @Header("Authorization") bearerToken: String,
        @Body requestBody: FirebasePushNotificationDto
    ): FirebasePushNotificationResponse
}