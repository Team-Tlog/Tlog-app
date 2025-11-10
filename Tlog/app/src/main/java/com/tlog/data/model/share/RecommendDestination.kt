package com.tlog.data.model.share

data class RecommendDestination(
    val imageUrl: String,
    val title: String,
    val description: String,
    val destinations: List<Destination>
)

data class Destination(
    val imageUrl: String,
    val name: String,
    val id: String
)