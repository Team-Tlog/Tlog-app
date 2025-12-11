package com.tlog.domain.model.course


data class AiTravel(
    val id: String,
    val name: String,
    val city: String,
    val description: String,
    val hashTags: List<String>,
    val imageUrl: String
)
