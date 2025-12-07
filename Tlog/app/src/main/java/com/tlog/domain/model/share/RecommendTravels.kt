package com.tlog.domain.model.share

import com.tlog.domain.model.travel.MinimalTravel

data class RecommendTravels(
    val title: String,
    val description: String,
    val imageUrl: String,
    val destinations: List<MinimalTravel>
)
