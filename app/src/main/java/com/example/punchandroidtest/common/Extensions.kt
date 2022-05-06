package com.example.punchandroidtest.common

import java.text.DecimalFormat

fun Int.formatNumber(): String {
    return DecimalFormat("\$###,###.###").format(this.toDouble())
}