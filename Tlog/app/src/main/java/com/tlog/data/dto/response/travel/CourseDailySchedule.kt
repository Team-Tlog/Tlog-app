package com.tlog.data.dto.response.travel

data class CourseDailySchedule(
    val dayNumber: Int,
    val groupedDestinations: Map<String, List<AiTravel>>
)
