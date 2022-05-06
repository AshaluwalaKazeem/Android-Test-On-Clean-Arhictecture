package com.example.punchandroidtest.domain.use_case.fetch_db

import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class FetchNoteFromDbUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
)
{
    operator fun invoke(): Flow<Resource<MutableList<Mars>>> = flow {
        emit(Resource.Loading())
        // Fetch data from local database
        val resource = marsRepository.loadFromDb()
        Timber.d("Database list size is ${resource.data?.size}")
        emit(resource)
    }
}