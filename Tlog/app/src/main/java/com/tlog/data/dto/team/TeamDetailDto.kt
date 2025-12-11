package com.tlog.data.dto.team

data class TeamDetailDto(
    val teamId: String,
    val teamName: String,
    val tbtiString: String,
    val inviteCode: String,
    val chatRoomId: Long,
    val createdAt: String,
    val expiredAt: String,
    val members: List<MemberDto>,
    val wishlist: List<WishlistDto>,
    val travelPlanDto: TravelPlanDto
)
