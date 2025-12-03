package com.tlog.data.dto.request.travel

import com.tlog.data.dto.travel.CartDto

data class AiRequest(
    val city: String,
    val region_codes: List<Int>,
    val dailyPlans: List<DailyPlanDto>,
    val wishlist: List<CartDto>
)