package com.tlog.util

import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun Long.toTimeString(): String {
    val now = System.currentTimeMillis()
    val diffMillis = abs(now - this)

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffMillis)

    return when {
        minutes < 3 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        hours < 24 -> "${hours}시간 전"
        else -> "${days}일 전"
    }
}