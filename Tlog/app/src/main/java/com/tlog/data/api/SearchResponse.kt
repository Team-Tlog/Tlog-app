package com.tlog.data.api

import com.tlog.data.model.travel.TravelDestinationResponse

data class SearchAndPageResponse(
    val totalElement: Int,
    val totalPage: Int,
    val content: List<TravelDestinationResponse>
)