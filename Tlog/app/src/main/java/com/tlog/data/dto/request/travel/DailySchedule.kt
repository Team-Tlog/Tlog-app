package com.tlog.data.dto.request.travel

data class DailySchedule(
    val dayNumber: Int,
    val date: String,
    val destinationIds: List<String>
)