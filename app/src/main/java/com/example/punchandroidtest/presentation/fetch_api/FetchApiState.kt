package com.example.punchandroidtest.presentation.fetch_api

import com.example.punchandroidtest.domain.model.Mars

data class FetchApiState(
    val isLoading: Boolean = false,
    val mars: List<Mars> = emptyList(),
    val error: String = ""
)
