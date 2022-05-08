package com.example.punchandroidtest.domain.use_case.update_db

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.db.dto.MarsEntity
import com.example.punchandroidtest.data.db.dto.toMars
import com.example.punchandroidtest.data.db.dto.toMarsEntity
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class UpdateDbUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
)
{
    operator fun invoke(mars: List<Mars>): Flow<Resource<String>> = flow {
        val marsEntities = mars.map { it.toMarsEntity() }
        marsRepository.updateDb(marsEntities)
        emit(Resource.Success("Done"))
    }
}