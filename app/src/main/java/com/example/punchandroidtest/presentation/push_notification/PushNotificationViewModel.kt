package com.example.punchandroidtest.presentation.push_notification

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationResponse
import com.example.punchandroidtest.domain.model.PushNotification
import com.example.punchandroidtest.domain.use_case.push_notification.PushNotificationUseCase
import com.example.punchandroidtest.domain.use_case.upload_image.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class PushNotificationViewModel
@Inject
constructor(
    private val pushNotificationUseCase: PushNotificationUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel()
{
    private val _state = mutableStateOf<PushNoticationState>(PushNoticationState())
    val state: State<PushNoticationState> = _state

    fun sendPushNotification(title: String, body: String, imageUri: Uri) {

        uploadImageUseCase(imageUri).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = PushNoticationState(isLoading = true)
                }
                is Resource.Success -> {
                    val pushNotification = PushNotification(title = title, body = body, image = result.data.toString())
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
                is Resource.Error -> {
                    _state.value = PushNoticationState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun queryFileName(uri: Uri, context: Context): String {
        val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    fun queryFileSize(uri: Uri, context: Context): Long {
        val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
        val sizeIndex: Int = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val fileSize: Long = returnCursor.getLong(sizeIndex)
        returnCursor.close()
        return fileSize
    }
}