package com.example.punchandroidtest.presentation.fetch_api

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.use_case.fetch_api.FetchApiUseCase
import com.example.punchandroidtest.domain.use_case.fetch_db.FetchNoteFromDbUseCase
import com.example.punchandroidtest.domain.use_case.save_note.SaveNoteToDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FetchApiViewModel
@Inject
constructor(
    private val fetchApiUseCase: FetchApiUseCase,
    private val saveNoteToDbUseCase: SaveNoteToDbUseCase
) : ViewModel()
{
    private val _state = mutableStateOf<FetchApiState>(FetchApiState())
    val state: State<FetchApiState> = _state

    init {
        fetchAPi()
    }

    private fun fetchAPi() {
        fetchApiUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = FetchApiState(isLoading = true)
                }
                is Resource.Success -> {
                    val list = FetchApiState(mars = result.data ?: emptyList())
                    Timber.d("List size is ${list.mars.size}")

                    saveNoteToDbUseCase(list.mars)
                    _state.value = list
                }
                is Resource.Error -> {
                    _state.value = FetchApiState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        fetchAPi()
    }

}