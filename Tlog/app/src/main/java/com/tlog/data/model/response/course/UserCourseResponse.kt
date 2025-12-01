package com.tlog.data.model.response.course

data class UserCourseResponse(
    val id: String,
    val startDate: String,
    val endDate: String,
    val dates: List<DateGroup>
)
