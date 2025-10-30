package com.tlog.data.model.team

data class DetailTeam(
    val teamId: String,
    val teamName: String,
    val tbtiString: String,
    val inviteCode: String,
    val chatRoomId: Long,
    val createdAt: String,
    val expiredAt: String,
    val members: List<Member>,
    val wishlist: List<WishlistItem>,
    val travelPlanDto: TravelPlanDto
) {
    // 편의 속성: travelPlanDto에서 날짜 정보 가져오기
    val startDate: String
        get() = travelPlanDto.startDate

    val endDate: String
        get() = travelPlanDto.endDate
}