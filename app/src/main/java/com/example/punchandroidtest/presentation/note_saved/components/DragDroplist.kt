package com.example.punchandroidtest.presentation.note_saved.components

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.presentation.note_saved.NoteSavedViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun DragDropList(
    mars: List<Mars>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteSavedViewModel
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf< Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)



        LazyColumn(
            modifier = modifier
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDrag = { change, offset ->
                            change.consumeAllChanges()
                            dragDropListState.onDrag(offset = offset)

                            if (overScrollJob?.isActive == true)
                                return@detectDragGesturesAfterLongPress

                            dragDropListState
                                .checkForOverScroll()
                                .takeIf { it != 0f }
                                ?.let {
                                    overScrollJob = scope.launch {
                                        dragDropListState.lazyListState.scrollBy(it)
                                    }
                                } ?: kotlin.run { overScrollJob?.cancel() }
                        },
                        onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                        onDragEnd = {
                            dragDropListState.onDragInterrupted()
                            viewModel.updateDb(mars)
                            Timber.d("Drag drop mars size ${mars.size}")
                        },
                        onDragCancel = { dragDropListState.onDragInterrupted() }
                    )
                }
                .fillMaxSize()
                .defaultMinSize(minHeight = 200.dp)
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            state = dragDropListState.lazyListState
        ) {
            itemsIndexed(mars) {index, item ->
                Column(
                    modifier = Modifier
                        .composed {
                            val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                                index == dragDropListState.currentIndexOfDraggedItem
                            }
                            Modifier.graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                        }
                        .fillMaxWidth()
                ) {
                    NoteSavedListItem(mars = item)
                }
            }
        }

}