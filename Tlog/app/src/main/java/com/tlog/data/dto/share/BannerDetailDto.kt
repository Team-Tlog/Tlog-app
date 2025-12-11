package com.tlog.data.dto.share

data class BannerDetailDto(
    val id: String,
    val name: String,
    val city: String,
    val location: LocationDto,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCountDto>
)
