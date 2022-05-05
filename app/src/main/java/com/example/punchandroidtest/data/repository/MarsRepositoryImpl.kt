package com.example.punchandroidtest.data.repository

import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.db.MarsDao
import com.example.punchandroidtest.data.db.dto.toMarsEntity
import com.example.punchandroidtest.data.remote.MarsServerApi
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
    private val marsDao: MarsDao
) : MarsRepository
{
    override suspend fun get(): Resource<List<Mars>> {
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

    override suspend fun save(mars: Mars): Resource<Mars> {
        val marsEntity = mars.toMarsEntity()
        return try{
            marsDao.insertMars(marsEntity)
            Resource.Success(mars)
        } catch (e : Exception) {
            Timber.d(e.fillInStackTrace())
            Resource.Error("An unexpected error occurred")
        }
    }
}