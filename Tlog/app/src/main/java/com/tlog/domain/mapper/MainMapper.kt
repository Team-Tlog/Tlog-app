package com.tlog.domain.mapper

import com.tlog.data.dto.share.LocalGuideDto
import com.tlog.domain.model.share.LocalGuide

fun LocalGuideDto.toDomain(): LocalGuide {
    return LocalGuide(
        title = title,
        imageUrl = imageUrl,
        infoUrl = infoUrl,
        description = description,
        hashTags = property
    )
}
