package com.example.punchandroidtest.common

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import java.text.DecimalFormat

fun Int.formatNumber(): String {
    return DecimalFormat("\$###,###.###").format(this.toDouble())
}