package com.tlog.data.model.request.team

data class CreateTeamRequest(
    val name: String,
    val creator: String,
    val travelPlan: TravelPlanBody
)
