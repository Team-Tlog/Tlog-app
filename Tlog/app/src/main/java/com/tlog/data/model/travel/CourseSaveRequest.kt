package com.tlog.data.model.travel

data class CourseSaveRequest(
    val startDate: String,
    val endDate: String,
    val dailySchedules: List<DailySchedule>
)
