package com.tlog.data.model.travel

data class DailySchedule(
    val dayNumber: Int,
    val date: String,
    val destinationIds: List<String>
)
