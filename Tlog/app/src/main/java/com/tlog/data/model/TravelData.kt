package com.tlog.data.model


// 추후 클래스 다이어그램 보고 수정하기
data class TravelData(
    val travelName: String,
    val description: String,
    val hashTags: List<String>,
    val cityName: String
)
