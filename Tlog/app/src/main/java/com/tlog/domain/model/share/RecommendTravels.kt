package com.tlog.domain.model.share

import com.tlog.domain.model.travel.TravelPreview

data class RecommendTravels(
    val title: String,
    val description: String,
    val imageUrl: String,
    val destinations: List<TravelPreview>
)
