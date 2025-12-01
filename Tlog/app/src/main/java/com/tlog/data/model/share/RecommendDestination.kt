package com.tlog.data.model.share

data class RecommendDestination(
    val imageUrl: String,
    val title: String,
    val description: String,
    val destinations: List<Destination>
)
