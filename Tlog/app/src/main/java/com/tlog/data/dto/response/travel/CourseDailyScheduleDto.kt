package com.tlog.data.dto.response.travel

data class CourseDailyScheduleDto(
    val dayNumber: Int,
    val groupedDestinations: Map<String, List<AiTravelDto>>
)
