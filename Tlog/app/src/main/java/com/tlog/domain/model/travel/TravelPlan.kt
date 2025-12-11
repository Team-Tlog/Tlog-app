package com.tlog.domain.model.travel

data class TravelPlan(
    val city: String,
    val regionList: List<String>,
    val hasPet: Boolean,
    val hasCar: Boolean,
    val startDate: String,
    val endDate: String,
    val visitCountPerDay: Map<String, Int>
)
