package com.example.punchandroidtest.domain.use_case.update_db

import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.db.dto.toMars
import com.example.punchandroidtest.data.db.dto.toMarsEntity
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateDbUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
)
{
    operator fun invoke(mars: Mars, id: String): Flow<Resource<Mars>> = flow {
        val marsEntity = mars.toMarsEntity()
        marsEntity.id = id
        marsRepository.updateDb(marsEntity)
        emit(Resource.Success(marsEntity.toMars()))
    }
}