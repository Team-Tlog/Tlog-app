package com.tlog.data.dto.share

data class RecommendDestinationsDto(
    val title: String,
    val description: String,
    val imageUrl: String,
    val destinations: List<DestinationDto>
)
