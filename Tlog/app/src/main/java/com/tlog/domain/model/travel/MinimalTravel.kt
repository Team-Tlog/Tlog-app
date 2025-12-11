package com.tlog.domain.model.travel

data class MinimalTravel(
    val destinationId: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val tags: List<String>
)
