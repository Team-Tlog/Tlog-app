package com.tlog.domain.mapper

import com.tlog.data.dto.response.restaurant.RestaurantDto
import com.tlog.domain.model.share.Restaurant

fun RestaurantDto.toDomain(): Restaurant {
    return Restaurant(
        title = place_name,
        description = category_name,
        address = road_address_name,
        imageUrl = images.firstOrNull()?.link ?: "",
        infoUrl = place_url
    )
}
