package com.tlog.data.dto.request.team

data class TravelPlanBody(
    val city: String,
    val regionList: List<String>,
    val hasPet: Boolean,
    val hasTransport: Boolean,
    val startDate: String,
    val endDate: String,
    val visitCountPerDay: Map<String, Int>
)
