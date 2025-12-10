package com.tlog.domain.model.travel

data class CartTravel(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val tags: List<String>,
    val latitude: String,
    val longitude: String
)