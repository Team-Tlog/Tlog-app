package com.tlog.data.model.response.travel

import com.tlog.data.model.response.travel.AiTravel

data class CourseResponse(
    val id: String,
    val ownerId: String,
    val ownerType: String,
    val startDate: String,
    val endDate: String,
    val duration: Int,
    val dailySchedules: List<CourseDailySchedule>
)
