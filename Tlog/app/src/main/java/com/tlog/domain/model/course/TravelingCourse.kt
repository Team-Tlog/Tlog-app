package com.tlog.domain.model.course

data class TravelingCourse(
    val id: String,
    val startDate: String,
    val endDate: String,
    val duration: Int,
    val dailySchedules: List<DailyCourse>
)
