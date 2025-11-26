package com.tlog.data.model.response.course

data class CourseItem(
    val id: String,
    val startDate: String,
    val endDate: String,
    val dates: List<DateGroup>
)
