package com.tlog.domain.mapper

import com.tlog.data.dto.travel.CartDto
import com.tlog.data.dto.travel.ScrapDto
import com.tlog.domain.model.travel.ViewTravel

fun ScrapDto.toDomain(): ViewTravel {
    return ViewTravel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        tags = tagCountList.map { it.tagName },
        latitude = location.latitude,
        longitude = location.longitude
    )
}

fun CartDto.toDomain(): ViewTravel {
    return ViewTravel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        tags = tagCountList.map { it.tagName },
        latitude = location.latitude,
        longitude = location.longitude
    )
}