package com.example.punchandroidtest.presentation.fetch_api

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punchandroidtest.common.Resource
import com.example.punchandroidtest.domain.use_case.fetch_api.FetchApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FetchApiViewModel
@Inject
constructor(
    private val fetchApiUseCase: FetchApiUseCase
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
                    _state.value = FetchApiState(mars = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FetchApiState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

}