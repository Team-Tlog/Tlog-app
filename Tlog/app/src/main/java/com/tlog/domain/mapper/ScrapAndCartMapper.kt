package com.tlog.domain.mapper

import com.tlog.data.dto.travel.CartDto
import com.tlog.data.dto.travel.ScrapDto
import com.tlog.domain.model.travel.CartTravel
import com.tlog.domain.model.travel.ScrapTravel

fun ScrapDto.toDomain(): ScrapTravel {
    return ScrapTravel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        tags = tagCountList.map { it.tagName },
        latitude = location.latitude,
        longitude = location.longitude
    )
}

fun CartDto.toDomain(): CartTravel {
    return CartTravel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        tags = tagCountList.map { it.tagName },
        latitude = location.latitude,
        longitude = location.longitude
    )
}