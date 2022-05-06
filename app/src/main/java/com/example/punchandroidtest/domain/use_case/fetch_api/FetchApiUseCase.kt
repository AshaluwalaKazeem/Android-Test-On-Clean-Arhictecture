package com.example.punchandroidtest.domain.use_case.fetch_api

import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchApiUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
)
{

    operator fun invoke(): Flow<Resource<List<Mars>>> = flow {
        emit(Resource.Loading())
        // Fetch data
        val resource = marsRepository.fetch()
        emit(resource)
    }
}