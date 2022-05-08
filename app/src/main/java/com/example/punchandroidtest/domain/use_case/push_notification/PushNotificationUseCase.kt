package com.example.punchandroidtest.domain.use_case.push_notification

import com.example.punchandroidtest.common.Constants
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationResponse
import com.example.punchandroidtest.data.remote.dto.toFirebasePushNotificationDto
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.model.PushNotification
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PushNotificationUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
) {

    operator fun invoke(pushNotification: PushNotification): Flow<Resource<FirebasePushNotificationResponse>> =
        flow {
            emit(Resource.Loading())
            // Push Notification
            val resource = marsRepository.sendPushNotification(
                bearerToken = Constants.FIREBASE_BEARER_TOKEN,
                firebasePushNotificationDto = pushNotification.toFirebasePushNotificationDto(topic = Constants.TOPIC)
            )
            emit(resource)
        }
}