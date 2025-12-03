package com.tlog.domain.model.travel

data class Travel(
    val travelId: String,
    val travelName: String,
    val city: String,
    val hashTags: List<String>,
    val rating: Double,
    val reviewCount: Int,
    val imageUrl: String
)
