package com.tlog.data.model.travel

data class AiRequest(
    val city: String,
    val region_codes: List<Int>,
    val dailyPlans: List<DailyPlan>,
    val wishlist: List<Cart>
)
