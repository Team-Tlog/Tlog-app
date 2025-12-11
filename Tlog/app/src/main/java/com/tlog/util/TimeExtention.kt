package com.tlog.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

fun String.toFormattedDate(): String {
    val instant = Instant.parse(this)

    val koreaZone = ZoneId.of("Asia/Seoul")
    val koreaTime = instant.atZone(koreaZone)

    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    return koreaTime.format(formatter)
}