package com.tlog.data.model.request.travel

data class DailySchedule(
    val dayNumber: Int,
    val date: String,
    val destinationIds: List<String>
)