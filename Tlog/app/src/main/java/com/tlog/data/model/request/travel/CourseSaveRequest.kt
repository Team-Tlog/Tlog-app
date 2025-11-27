package com.tlog.data.model.request.travel

import com.tlog.data.model.request.travel.DailySchedule

data class CourseSaveRequest(
    val startDate: String,
    val endDate: String,
    val dailySchedules: List<DailySchedule>
)