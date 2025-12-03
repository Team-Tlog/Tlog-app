package com.tlog.data.dto.share

data class RecommendDestinationDto(
    val title: String,
    val description: String,
    val imageUrl: String,
    val destinations: List<DestinationDto>
)
