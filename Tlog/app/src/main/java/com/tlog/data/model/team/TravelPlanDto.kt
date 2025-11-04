package com.tlog.data.model.team

data class TravelPlanDto(
    val city: String,
    val regionList: List<String>,
    val hasPet: Boolean,
    val hasTransport: Boolean,
    val startDate: String,
    val endDate: String,
    val visitCountPerDay: Map<String, Int>
)