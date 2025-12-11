package com.tlog.domain.mapper

import com.tlog.data.dto.team.MemberDto
import com.tlog.data.dto.team.MemberSimpleDto
import com.tlog.data.dto.team.TeamDetailDto
import com.tlog.data.dto.team.TeamDto
import com.tlog.domain.model.team.Member
import com.tlog.domain.model.team.MinimalMember
import com.tlog.domain.model.team.Team
import com.tlog.domain.model.team.TeamDetail

fun MemberSimpleDto.toDomain(): MinimalMember {
    return MinimalMember(
        id = memberId,
        tbtiString = tbtiString,
        profileImageUrl = profileImage ?: ""
    )
}

fun TeamDto.toDomain(): Team {
    return Team(
        id = teamId,
        teamName = teamName,
        leaderId = teamLeaderId,
        leaderName = teamLeaderName,
        members = memberSimpleDtoList.map { it.toDomain() },
        travelPlan = travelPlanDto.toDomain()
    )
}

fun MemberDto.toDomain(): Member {
    return Member(
        userId = userId,
        profileImageUrl = profileImageUrl ?: "",
        name = name,
        tbtiString = tbtiString,
        isLeader = isLeader
    )
}

fun TeamDetailDto.toDomain(): TeamDetail {
    return TeamDetail(
        teamId = teamId,
        teamName = teamName,
        tbtiString = tbtiString,
        inviteCode = inviteCode,
        chatRoomId = chatRoomId,
        members = members.map { it.toDomain() },
        wishlist = wishlist.map { it.toDomain() },
        travelPlan = travelPlanDto.toDomain()
    )
}
