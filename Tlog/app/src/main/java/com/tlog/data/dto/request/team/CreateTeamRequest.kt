package com.tlog.data.dto.request.team

data class CreateTeamRequest(
    val name: String,
    val creator: String,
    val travelPlan: TravelPlanBody
)
