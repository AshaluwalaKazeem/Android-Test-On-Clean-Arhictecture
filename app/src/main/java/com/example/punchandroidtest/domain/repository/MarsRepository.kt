package com.example.punchandroidtest.domain.repository

import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.model.Mars

interface MarsRepository {

    suspend fun get() : Resource<List<Mars>>

    suspend fun save(mars: Mars): Resource<Mars>
}