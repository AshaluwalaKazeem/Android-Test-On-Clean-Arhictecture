package com.example.punchandroidtest.presentation.note_saved

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.punchandroidtest.domain.model.Mars

data class NoteSavedState (
    val isLoading: Boolean = false,
    val mars: SnapshotStateList<Mars> = mutableStateListOf<Mars>(),
    val error: String = ""
)