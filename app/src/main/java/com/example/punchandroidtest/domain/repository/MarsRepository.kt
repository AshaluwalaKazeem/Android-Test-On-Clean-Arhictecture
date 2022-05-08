package com.example.punchandroidtest.domain.repository

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.db.dto.MarsEntity
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationDto
import com.example.punchandroidtest.data.remote.dto.FirebasePushNotificationResponse
import com.example.punchandroidtest.domain.model.Mars

interface MarsRepository {

    suspend fun fetch() : Resource<List<Mars>>

    suspend fun save(mars: List<Mars>): Resource<List<Mars>>

    suspend fun loadFromDb() : Resource<SnapshotStateList<Mars>>

    suspend fun updateDb(marsEntities: List<MarsEntity>)

    suspend fun sendPushNotification(bearerToken: String, firebasePushNotificationDto: FirebasePushNotificationDto): Resource<FirebasePushNotificationResponse>

    suspend fun uploadImage(file: Uri) : Resource<Uri>
}