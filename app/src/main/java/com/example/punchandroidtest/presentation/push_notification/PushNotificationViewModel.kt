package com.example.punchandroidtest.presentation.push_notification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationResponse
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.model.PushNotification
import com.example.punchandroidtest.domain.use_case.push_notification.PushNotificationUseCase
import com.example.punchandroidtest.presentation.note_saved.NoteSavedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PushNotificationViewModel
@Inject
constructor(
    private val pushNotificationUseCase: PushNotificationUseCase
) : ViewModel()
{
    private val _state = mutableStateOf<PushNoticationState>(PushNoticationState())
    val state: State<PushNoticationState> = _state

    private fun sendPushNotification(pushNotification: PushNotification) {
        pushNotificationUseCase(pushNotification).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = PushNoticationState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = PushNoticationState(response = result.data ?: FirebasePushNotificationResponse())
                }
                is Resource.Error -> {
                    _state.value = PushNoticationState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}