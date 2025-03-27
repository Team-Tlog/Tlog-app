package com.tlog.data.model

import android.net.Uri

data class TeamData(
    val teamName: String,
    val teamTBTI: String? = null,
    val teamStartDate: String? = null,
    val teamEndDate: String? = null,
    val members: List<TeamMember> = emptyList(),
    val teamDestination: String? = null,
    val teamLeader: String? = null,
    val memberImage: List<String> = emptyList()
)

data class TeamMember(
    val name: String,
    val tbti: String,
    val imageResId: Int
)