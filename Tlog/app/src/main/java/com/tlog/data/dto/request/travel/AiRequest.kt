package com.tlog.data.dto.request.travel

import com.tlog.data.dto.travel.Cart

data class AiRequest(
    val city: String,
    val region_codes: List<Int>,
    val dailyPlans: List<DailyPlan>,
    val wishlist: List<Cart>
)