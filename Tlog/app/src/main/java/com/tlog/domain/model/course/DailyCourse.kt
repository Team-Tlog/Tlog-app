package com.tlog.domain.model.course

data class DailyCourse(
    val dayNumber: Int,
    val travels: List<AiTravel>
)
