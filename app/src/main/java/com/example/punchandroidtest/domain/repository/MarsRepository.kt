package com.example.punchandroidtest.domain.repository

import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.data.db.dto.MarsEntity
import com.example.punchandroidtest.domain.model.Mars

interface MarsRepository {

    suspend fun fetch() : Resource<List<Mars>>

    suspend fun save(mars: List<Mars>): Resource<List<Mars>>

    suspend fun loadFromDb() : Resource<MutableList<Mars>>

    suspend fun updateDb(marsEntity: MarsEntity)
}