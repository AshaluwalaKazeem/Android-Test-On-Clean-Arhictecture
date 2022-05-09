package com.example.punchandroidtest.presentation.note_saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.punchandroidtest.presentation.note_saved.components.DragDropList
import com.example.punchandroidtest.presentation.note_saved.components.move
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import timber.log.Timber

@Composable
fun NoteSavedScreen(
    viewModel: NoteSavedViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()){

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = viewModel.state.value.isLoading),
            swipeEnabled = !viewModel.state.value.isLoading,
            onRefresh = { viewModel.refresh()},
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.mars.isNotEmpty()) {
                DragDropList(mars = state.mars, onMove = { fromIndex, toIndex ->
                    state.mars.move(fromIndex, toIndex)
                }, viewModel = viewModel)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item { }
                }
            }
        }
        if(state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}