package com.example.punchandroidtest.data.repository

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.db.MarsDao
import com.example.punchandroidtest.data.db.dto.MarsEntity
import com.example.punchandroidtest.data.db.dto.toMars
import com.example.punchandroidtest.data.db.dto.toMarsEntity
import com.example.punchandroidtest.data.remote.FirebaseApi
import com.example.punchandroidtest.data.remote.MarsServerApi
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationDto
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationResponse
import com.example.punchandroidtest.data.remote.dto.toMars
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.random.Random


class MarsRepositoryImpl
@Inject
constructor(
    private val marsServerApi: MarsServerApi,
    private val marsDao: MarsDao,
    private val firebaseApi: FirebaseApi
) : MarsRepository {
    override suspend fun fetch(): Resource<List<Mars>> {
        return try {
            val marsBlogs = marsServerApi.getMars()
            val marsList = marsBlogs.map { it.toMars() }
            Resource.Success(marsList)
        } catch (e: HttpException) {
            Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server. Check your internet connection")
        } catch (e: Exception) {
            Timber.d(e.fillInStackTrace())
            Resource.Error(message = "An unexpected error occurred")
        }
    }

    override suspend fun save(mars: List<Mars>): Resource<List<Mars>> {
        val marsEntity = mars.map { it.toMarsEntity() }
        return try {
            marsDao.insertAll(marsEntity)
            Resource.Success(mars)
        } catch (e: Exception) {
            Timber.e(e.fillInStackTrace())
            Resource.Error("An unexpected error occurred")
        }
    }

    override suspend fun loadFromDb(): Resource<SnapshotStateList<Mars>> {
        try {
            val marsList = marsDao.get().map { it.toMars() }
            if (marsList.isNullOrEmpty()) {
                return Resource.Error("No records found in db. Please swipe to refresh")
            }
            return Resource.Success(marsDao.get().map { it.toMars() }.toMutableStateList())
        } catch (e: Exception) {
            Timber.d(e.fillInStackTrace())
            return Resource.Error("An unexpected error occurred. Please swipe to refresh")
        }
    }

    override suspend fun updateDb(marsEntities: List<MarsEntity>) {
        marsDao.updateDb(marsEntities)
    }

    override suspend fun sendPushNotification(
        bearerToken: String,
        firebasePushNotificationDto: FirebasePushNotificationDto
    ): Resource<FirebasePushNotificationResponse> {
        return try {
            val response = firebaseApi.sendPushNotification(
                bearerToken = bearerToken,
                requestBody = firebasePushNotificationDto
            )
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach firebase server. Check your internet connection")
        } catch (e: Exception) {
            Timber.d(e.fillInStackTrace())
            Resource.Error(message = "An unexpected error occurred")
        }
    }

    override suspend fun uploadImage(file: Uri): Resource<Uri> =
        suspendCancellableCoroutine { continuation ->
            try {
                val storage = FirebaseStorage.getInstance()

                val storageRef = storage.reference
                val imageRef = storageRef.child("${Random.nextInt()}.jpg")
                val uploadTask: UploadTask = imageRef.putFile(file)
                /*uploadTask.addOnFailureListener(OnFailureListener {
                    // Handle unsuccessful uploads
                    if (continuation.isActive) {
                        continuation.resume(
                            Resource.Error(message = it.localizedMessage ?: "An unexpected error occurred")
                        )
                    }
                }).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {

                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                })*/
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        if (continuation.isActive) {
                            task.exception?.let {
                                continuation.resume(
                                    Resource.Error(
                                        message = it.localizedMessage
                                            ?: "An unexpected error occurred"
                                    )
                                )
                            }
                            continuation.resume(
                                Resource.Error(message = "An unexpected error occurred")
                            )
                        }
                    }
                    imageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        if (continuation.isActive) {
                            continuation.resume(
                                Resource.Success(downloadUri)
                            )
                        }
                    } else {
                        // Handle failures
                        // ...
                        if (continuation.isActive) {
                            task.exception?.let {
                                continuation.resume(
                                    Resource.Error(
                                        message = it.localizedMessage
                                            ?: "An unexpected error occurred"
                                    )
                                )
                            }
                            continuation.resume(
                                Resource.Error(message = "An unexpected error occurred")
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.d(e.fillInStackTrace())
                if (continuation.isActive) {
                    continuation.resume(
                        Resource.Error(
                            message = e.localizedMessage ?: "An unexpected error occurred"
                        )
                    )
                }
            }
        }
}