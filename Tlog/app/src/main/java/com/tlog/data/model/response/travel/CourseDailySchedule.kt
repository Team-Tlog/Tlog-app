package com.tlog.data.model.response.travel

data class CourseDailySchedule(
    val dayNumber: Int,
    val groupedDestinations: Map<String, List<AiTravel>>
)
