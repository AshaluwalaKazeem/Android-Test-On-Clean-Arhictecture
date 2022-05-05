package com.example.punchandroidtest.data.remote

import com.example.punchandroidtest.data.remote.dto.MarsDto
import retrofit2.http.GET

interface MarsServerApi {

    @GET("realestate")
    suspend fun getMars(): List<MarsDto>
}