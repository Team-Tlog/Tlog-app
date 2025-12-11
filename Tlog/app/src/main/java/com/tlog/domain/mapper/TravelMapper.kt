package com.tlog.domain.mapper

import com.tlog.data.dto.response.travel.PopularDestinationDto
import com.tlog.data.dto.response.travel.TravelDestinationDto
import com.tlog.data.dto.response.travel.TravelSearchDto
import com.tlog.data.dto.team.TravelPlanDto
import com.tlog.data.dto.team.WishlistDto
import com.tlog.domain.model.travel.PopularTravel
import com.tlog.domain.model.travel.Travel
import com.tlog.domain.model.travel.TravelPlan
import com.tlog.domain.model.travel.ViewTravel

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

fun TravelSearchDto.toDomain(): ViewTravel {
    return ViewTravel(
        id = id,
        name = name,
        tags = tagCountList.map { it.tagName },
        imageUrl = imageUrl,
        description = description,
        latitude = location.latitude,
        longitude = location.longitude
    )
}

fun PopularDestinationDto.toDomain(): PopularTravel {
    return PopularTravel(
        id = destinationId,
        city = region,
        imageUrl = imageUrl
    )
}

fun TravelPlanDto.toDomain(): TravelPlan {
    return TravelPlan(
        city = city,
        regionList = regionList,
        hasPet = hasPet,
        hasCar = hasTransport,
        startDate = startDate,
        endDate = endDate,
        visitCountPerDay = visitCountPerDay
    )
}

fun WishlistDto.toDomain(): ViewTravel {
    return ViewTravel(
        id = id,
        name = name,
        tags = tagCountList.map { it.tagName },
        imageUrl = imageUrl,
        description = description,
        latitude = location.latitude,
        longitude = location.longitude
    )
}
