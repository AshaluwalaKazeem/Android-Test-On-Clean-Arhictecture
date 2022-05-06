package com.example.punchandroidtest.presentation.note_saved.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.punchandroidtest.domain.model.Mars
import com.example.punchandroidtest.presentation.note_saved.NoteSavedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun DragDropList(
    items: MutableList<Mars>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteSavedViewModel
) {
    val scope = rememberCoroutineScope()

    var overscrollJob by remember { mutableStateOf<Job?>(null) }

    val dragDropListState = rememberDragDropListState(onMove = onMove)

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consumeAllChanges()
                        dragDropListState.onDrag(offset)

                        if (overscrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        dragDropListState.checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let { overscrollJob = scope.launch { dragDropListState.lazyListState.scrollBy(it) } }
                            ?: run { overscrollJob?.cancel() }
                    },
                    onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                    onDragEnd = {
                        Timber.d("onDragEnd")
                        dragDropListState.onDragInterrupted { startIndex: Int, endIndex: Int ->
                            viewModel.updateDb(items[endIndex], items[startIndex].id.value)
                            viewModel.updateDb(items[startIndex], items[endIndex].id.value)

                            var startData = items[startIndex]
                            var id2 = items[endIndex].id.value
                            var imageSource2 = items[endIndex].imageSource.value
                            var price2 = items[endIndex].price.value
                            var type2 = items[endIndex].type.value

                            items[endIndex].id.value = startData.id.value
                            items[endIndex].imageSource.value = startData.imageSource.value
                            items[endIndex].price.value = startData.price.value
                            items[endIndex].type.value = startData.type.value

                            items[startIndex].id.value = id2
                            items[startIndex].imageSource.value = imageSource2
                            items[startIndex].price.value = price2
                            items[startIndex].type.value = type2
                        }
                    },
                    onDragCancel = {
                        Timber.d("onDragCancel")
                        dragDropListState.onDragInterrupted{ startIndex, endIndex ->

                        }
                    }
                )
            },
        state = dragDropListState.lazyListState
    ) {
        itemsIndexed(items) { index, item ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull =
                            dragDropListState.elementDisplacement.takeIf {
                                index == dragDropListState.currentIndexOfDraggedItem
                            }

                        Modifier
                            .graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                    }
                    .fillMaxWidth()
            ) { NoteSavedListItem(item) }
        }
    }
}