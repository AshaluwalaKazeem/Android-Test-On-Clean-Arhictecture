package com.example.punchandroidtest.presentation.note_saved

import com.example.punchandroidtest.domain.model.Mars

data class NoteSavedState (
    val isLoading: Boolean = false,
    val mars: MutableList<Mars> = mutableListOf(),
    val error: String = ""
)