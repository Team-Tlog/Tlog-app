package com.tlog.domain.model.team

import com.tlog.domain.model.travel.TravelPlan

data class Team(
    val id: String,
    val teamName: String,
    val leaderId: String,
    val leaderName: String,
    val members: List<MinimalMember>,
    val travelPlan: TravelPlan
)
