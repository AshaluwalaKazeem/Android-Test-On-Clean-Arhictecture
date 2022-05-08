package com.example.punchandroidtest.presentation.push_notification

import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationResponse
import com.example.punchandroidtest.domain.model.Mars

data class PushNoticationState(
    val isLoading: Boolean = false,
    val response: FirebasePushNotificationResponse = FirebasePushNotificationResponse(),
    val error: String = ""
)