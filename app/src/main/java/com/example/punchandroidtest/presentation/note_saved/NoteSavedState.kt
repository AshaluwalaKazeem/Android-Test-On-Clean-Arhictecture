package com.example.punchandroidtest.presentation.note_saved

import com.example.punchandroidtest.domain.model.Mars

data class NoteSavedState (
    val isLoading: Boolean = false,
    val mars: List<Mars> = emptyList(),
    val error: String = ""
)