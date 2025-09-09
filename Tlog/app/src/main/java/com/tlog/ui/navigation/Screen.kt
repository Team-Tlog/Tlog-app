package com.tlog.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Main: Screen

    @Serializable
    data object Login: Screen

    @Serializable
    data object Map: Screen


    // Sns
    @Serializable
    data object SnsMain: Screen

    @Serializable
    data object SnsId: Screen

    @Serializable
    data object Sns: Screen

    @Serializable
    data class SnsPostDetail(val postId: String): Screen

    @Serializable
    data object SnsSearch: Screen

    @Serializable
    data class SnsMyPage(val userId: String): Screen

    @Serializable
    data object SnsPostWrite: Screen


    // Review
    @Serializable
    data class ReviewWrite(val travelId: String, val travelName: String): Screen

    @Serializable
    data class ReviewList(val travelId: String, val travelName: String): Screen


    // Travel
    @Serializable
    data object ScrapAndCart: Screen

    @Serializable
    data object AddTravel: Screen

    @Serializable
    data class TravelList(val title: String, val city: String?): Screen

    @Serializable
    data class TravelInfo(val travelId: String): Screen


    // Team
    @Serializable
    data object TeamList: Screen

    @Serializable
    data object CreateTeam: Screen

    @Serializable
    data class TeamInfoInput(val teamName: String): Screen

    @Serializable
    data class TeamDetail(val teamId: String): Screen

    @Serializable
    data object JoinTeam: Screen

    //Search
    @Serializable
    data object Search: Screen

    @Serializable
    data object SearchReview: Screen


    // TBTI
    @Serializable
    data object TbtiTest: Screen

    @Serializable
    data object TbtiCodeInput: Screen

    @Serializable
    data object TbtiIntro: Screen

    @Serializable
    data class TbtiResult(
        val tbtiResultCode: String,
        val sValue: String,
        val eValue: String,
        val lValue: String,
        val aValue: String
    )


    // MyPage
    @Serializable
    data object MyPage: Screen

    @Serializable
    data object Report: Screen

    @Serializable
    data object Course: Screen

    @Serializable
    data object Notification: Screen
}