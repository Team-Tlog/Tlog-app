package com.tlog.data.dto.request.travel

data class CourseSaveRequest(
    val startDate: String,
    val endDate: String,
    val dailySchedules: List<DailyScheduleDto>
)