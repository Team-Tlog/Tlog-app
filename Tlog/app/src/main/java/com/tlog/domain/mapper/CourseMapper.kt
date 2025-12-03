package com.tlog.domain.mapper

import com.tlog.data.dto.response.travel.AiTravelDto
import com.tlog.data.dto.response.travel.CourseDailyScheduleDto
import com.tlog.data.dto.response.travel.CourseResponse
import com.tlog.domain.model.course.AiCourse
import com.tlog.domain.model.course.AiTravel
import com.tlog.domain.model.course.DailyCourse

fun AiTravelDto.toDomain(): AiTravel {
    return AiTravel(
        id = id,
        name = name,
        city = city,
        description = description,
        hashTags = tagCountList?.map { it.tagName } ?: emptyList(),
        imageUrl = imageUrl ?: ""
    )
}

fun Map<String, List<AiTravelDto>>.toDomain(): List<AiCourse> {
    return map { (city, travels) ->
        AiCourse(
            city = city,
            aiTravels = travels.map { it.toDomain() }
        )
    }
}

//fun CourseDailyScheduleDto.toDomain(): DailyCourse {
//    return DailyCourse(
//        dayNumber = dayNumber,
//        travels = groupedDestinations.toDomain()
//    )
//}

fun CourseResponse.toDomain(): List<DailyCourse> {
    return dailySchedules.map {
        DailyCourse(
            dayNumber = it.dayNumber,
            travels = it.groupedDestinations.values.flatten().map { dto -> dto.toDomain() }
        )
    }
}