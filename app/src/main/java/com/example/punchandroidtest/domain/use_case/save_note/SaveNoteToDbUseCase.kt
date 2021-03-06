package com.example.punchandroidtest.domain.use_case.save_note

import android.util.Log
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class SaveNoteToDbUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
)
{
    suspend operator fun invoke(mars: List<Mars>) {
        // Save
        marsRepository.save(mars)
    }
}