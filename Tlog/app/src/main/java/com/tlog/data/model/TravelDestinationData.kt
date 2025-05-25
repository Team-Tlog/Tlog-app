package com.tlog.data.model

data class TravelDestinationData(
    val id: String,
    val name: String,
    val location: String,
    val rating: Double,
    val reviewCount: Int,
    val tags: List<String>,
    val isFavorite: Boolean = false
)
