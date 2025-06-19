package com.tlog.data.api

import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount

data class ScrapData(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCount>,
    val location: Location
)

