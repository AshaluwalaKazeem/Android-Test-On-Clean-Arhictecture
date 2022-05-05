package com.example.punchandroidtest.domain.use_case.save_note

import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SaveNoteToDbUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
)
{
    operator fun invoke(mars: Mars): Flow<Resource<Mars>> = flow {
        emit(Resource.Loading())
        // Save
        val resource = marsRepository.save(mars)
        emit(resource)
    }
}