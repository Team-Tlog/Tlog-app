package com.tlog.data.model.tmp

import com.tlog.data.model.travel.Review


// 추후 클래스 다이어그램 보고 수정하기
data class TmpTravelData(
    val travelName: String,
    val description: String,
    val hashTags: List<String>,
    val cityName: String,
    val avgStarRating: Double,
    val starRatings: List<Int>, // 0 -> 1점 리뷰 1 -> 2점리뷰 ... 4 -> 5점 리뷰 개수
    val reviewList: List<Review>
)
