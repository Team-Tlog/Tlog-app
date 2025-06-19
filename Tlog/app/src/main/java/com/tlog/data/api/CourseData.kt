package com.tlog.data.api

import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount

data class UserCourseResponse(
    val id: String,
    val startDate: String,
    val endDate: String,
    val dates: List<DateGroup>
)




data class DateGroup(
    val destinationGroups: List<DestinationGroup>
)

data class DestinationGroup(
    val groupName: String,
    val destinations: List<UserCourseDestination>
)

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