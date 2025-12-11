package com.tlog.domain.mapper

import com.tlog.data.dto.share.BannerDto
import com.tlog.data.dto.share.DestinationDto
import com.tlog.data.dto.share.LocalGuideDto
import com.tlog.data.dto.share.PostDto
import com.tlog.data.dto.share.RecommendDestinationsDto
import com.tlog.domain.model.share.Banner
import com.tlog.domain.model.share.LocalGuide
import com.tlog.domain.model.share.RecommendPost
import com.tlog.domain.model.share.RecommendTravels
import com.tlog.domain.model.travel.TravelPreview

fun LocalGuideDto.toDomain(): LocalGuide {
    return LocalGuide(
        title = title,
        imageUrl = imageUrl,
        infoUrl = infoUrl,
        description = description,
        hashTags = property
    )
}


fun PostDto.toDomain(): RecommendPost {
    return RecommendPost(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls
    )
}

fun DestinationDto.toDomain(): TravelPreview {
    return TravelPreview(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}

fun RecommendDestinationsDto.toDomain(): RecommendTravels {
    return RecommendTravels(
        title = title,
        description = description,
        imageUrl = imageUrl,
        destinations = destinations.map { it.toDomain() }
    )
}

fun BannerDto.toDomain(): Banner {
    return Banner(
        id = id,
        imageUrl = imageUrl,
        title = title,
        hashtags = hashtags
    )
}
