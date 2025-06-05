package com.tlog.data.api

import com.tlog.data.model.travel.Travel

data class TeamData(
    val teamId: String,
    val teamName: String,
    val teamLeaderName: String,
    val memberIdList: List<String>
)


data class TeamDetailData(
    val teamId: String,
    val teamName: String,
    val inviteCode: String,
    val startDate: String,
    val endDate: String,
    val members: List<MemberData>,
    val wishlist: List<Travel>
)


data class MemberData(
    val memberId: String,
    val memberName: String,
    val isLeader: Boolean
)