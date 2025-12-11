package com.tlog.domain.model.team

import com.tlog.domain.model.travel.TravelPlan
import com.tlog.domain.model.travel.ViewTravel

data class TeamDetail(
    val teamId: String,
    val teamName: String,
    val tbtiString: String,
    val inviteCode: String,
    val chatRoomId: Long,
    val members: List<Member>,
    val wishlist: List<ViewTravel>,
    val travelPlan: TravelPlan
)
