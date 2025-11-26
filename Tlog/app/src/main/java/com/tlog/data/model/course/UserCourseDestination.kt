package com.tlog.data.model.course

import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount

data class UserCourseDestination(
    val id: String,
    val name: String,
    val city: String,
    val location: Location,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val tagCountList: List<TagCount>
)
