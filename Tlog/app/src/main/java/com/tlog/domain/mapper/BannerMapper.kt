package com.tlog.domain.mapper

import com.tlog.data.dto.response.travel.BannerTravelResponse
import com.tlog.domain.model.travel.BannerDetail

fun BannerTravelResponse.toDomain(): BannerDetail {
    return BannerDetail(
        title = title,
        travels = destinations.content.toDomain()
    )
}
