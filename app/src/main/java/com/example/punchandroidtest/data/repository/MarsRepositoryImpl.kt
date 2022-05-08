package com.example.punchandroidtest.data.repository

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
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


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

    override suspend fun loadFromDb(): Resource<MutableList<Mars>> {
        return try {
            Resource.Success(marsDao.get().map { it.toMars() }.toMutableList())
        } catch (e: Exception) {
            Timber.d(e.fillInStackTrace())
            Resource.Error("An unexpected error occurred")
        }
    }

    override suspend fun updateDb(marsEntity: MarsEntity) {
        marsDao.updateDb(marsEntity)
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
}