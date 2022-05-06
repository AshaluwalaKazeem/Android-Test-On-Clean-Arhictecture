package com.example.punchandroidtest.domain.model

import androidx.compose.runtime.MutableState

data class Mars(
    val id: MutableState<String>,
    val imageSource: MutableState<String>,
    val price: MutableState<Int>,
    val type: MutableState<String>
)
