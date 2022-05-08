package com.example.punchandroidtest.presentation.note_saved

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.domain.use_case.fetch_api.FetchApiUseCase
import com.example.punchandroidtest.domain.use_case.fetch_db.FetchNoteFromDbUseCase
import com.example.punchandroidtest.domain.use_case.save_note.SaveNoteToDbUseCase
import com.example.punchandroidtest.domain.use_case.update_db.UpdateDbUseCase
import com.example.punchandroidtest.presentation.fetch_api.FetchApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class NoteSavedViewModel
@Inject
constructor(
    private val fetchNoteFromDbUseCase: FetchNoteFromDbUseCase,
    private val updateDbUseCase: UpdateDbUseCase
) : ViewModel()
{
    private val _state = mutableStateOf<NoteSavedState>(NoteSavedState())
    val state: State<NoteSavedState> = _state

    init {
        fetchDb()
    }

    private fun fetchDb() {
        fetchNoteFromDbUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = NoteSavedState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = NoteSavedState(mars = result.data ?: mutableStateListOf<Mars>())
                }
                is Resource.Error -> {
                    _state.value = NoteSavedState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateDb(mars: List<Mars>) {
        updateDbUseCase(mars = mars)
            .onEach {

            }.launchIn(viewModelScope)
    }
}