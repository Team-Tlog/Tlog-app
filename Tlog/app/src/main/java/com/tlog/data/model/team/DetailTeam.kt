package com.tlog.data.model.team

import com.tlog.data.model.travel.Travel

data class DetailTeam(
    val teamId: String,
    val teamName: String,
    val inviteCode: String,
    val startDate: String,
    val endDate: String,
    val members: List<Member>,
    val wishlist: List<Travel>
)