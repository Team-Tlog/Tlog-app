package com.tlog.data.model.share

data class BannerItem(
    val id: String,
    val name: String,
    val city: String,
    val location: Location,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCount>
)
