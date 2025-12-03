package com.tlog.domain.mapper

import com.tlog.data.dto.response.travel.TravelDestinationDto
import com.tlog.domain.model.travel.Travel

fun TravelDestinationDto.toDomain(): Travel {
    return Travel(
        travelId = id,
        travelName = name,
        city = city,
        hashTags = tagCountList.map { it.tagName },
        rating = averageRating,
        reviewCount = reviewCount,
        imageUrl = imageUrl
    )
}

fun List<TravelDestinationDto>.toDomain(): List<Travel> = map { it.toDomain() }
