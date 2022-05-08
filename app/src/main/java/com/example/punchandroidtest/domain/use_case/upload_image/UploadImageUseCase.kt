package com.example.punchandroidtest.domain.use_case.upload_image

import android.net.Uri
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.db.dto.toMars
import com.example.punchandroidtest.data.db.dto.toMarsEntity
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.repository.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadImageUseCase
@Inject
constructor(
    private val marsRepository: MarsRepository
)
{
    operator fun invoke(fileUri: Uri): Flow<Resource<Uri>> = flow {
        emit(Resource.Loading())
        // Fetch data
        val resource = marsRepository.uploadImage(fileUri)
        emit(resource)
    }
}