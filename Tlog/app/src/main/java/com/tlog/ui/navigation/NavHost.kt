package com.tlog.ui.navigation

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.tlog.api.retrofit.TokenProvider
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.ui.screen.beginning.TbtiCodeInputScreen
import com.tlog.ui.screen.beginning.TbtiResultScreen
import com.tlog.ui.screen.beginning.TbtiTestScreen
import com.tlog.ui.screen.review.AddTravelDestinationScreen
import com.tlog.ui.screen.review.ReviewListScreen
import com.tlog.ui.screen.review.ReviewWritingScreen
import com.tlog.ui.screen.review.SelectReviewWriteScreen
import com.tlog.ui.screen.share.ScrapAndCartScreen
import com.tlog.ui.screen.share.MainScreen
import com.tlog.ui.screen.share.MapScreen
import com.tlog.ui.screen.share.MyPageScreen
import com.tlog.ui.screen.share.NotificationScreen
import com.tlog.ui.screen.sns.SNSIdCreateScreen
import com.tlog.ui.screen.sns.SNSScreen
import com.tlog.ui.screen.sns.SnsDetailScreen
import com.tlog.ui.screen.sns.SnsMyPageScreen
import com.tlog.ui.screen.sns.SnsPostWriteDetailScreen
import com.tlog.ui.screen.sns.SnsSearchScreen
import com.tlog.ui.screen.team.MyTeamListScreen
import com.tlog.ui.screen.team.TeamDetailScreen
import com.tlog.ui.screen.team.TeamJoinByCode
import com.tlog.ui.screen.team.TeamNameCreateScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.SearchScreen
import com.tlog.ui.screen.travel.TravelDestinationRecommendation
import com.tlog.ui.screen.travel.TravelInfoScreen
import com.tlog.viewmodel.beginning.TbtiCodeInputViewModel
import com.tlog.viewmodel.beginning.TbtiTestViewModel
import com.tlog.viewmodel.beginning.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun NavHost(
    navController: NavHostController,
    startScreen: String,
    loginViewModel: LoginViewModel,
    launcher: ActivityResultLauncher<Intent>,
    googleSignInClient: GoogleSignInClient
) {
    val viewModel: MyNavViewModel = hiltViewModel() // 고민 좀 해볼건데 일단 이렇게


    NavHost(navController = navController, startDestination = "login") {
        // Main
        composable("main") {
            MainScreen(navController = navController)
        }


        // Login
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onGoogleLoginClick = {
                    val intent = googleSignInClient.signInIntent
                    launcher.launch(intent)
                },
                navController = navController
            )
        }

        // Map (지도에서 보기)
        composable("map") {
            MapScreen()
        }


        //SNS
        composable("snsMain") {
            SNSScreen(navController = navController)
        }
        composable("snsId") {
            SNSIdCreateScreen(navController = navController)
        }
        composable("sns") {
            val snsId = viewModel.tokenProvider.getSnsId()
            Log.d("sns", "snsId: ${snsId}")
            if (snsId == null || snsId.isEmpty())
                SNSIdCreateScreen(navController = navController)
            else
                SNSScreen(navController = navController)
        }
        composable("snsPostDetail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: return@composable
            SnsDetailScreen(postId = postId, navController = navController)
        }
        composable("snsSearch") {
            SnsSearchScreen(navController = navController)
        }
        composable("snsMyPage/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable

            SnsMyPageScreen(
                navController = navController,
                userId = userId
            )
        }



        composable("post2") {
            SnsPostWriteDetailScreen()
        }

        // Review
        composable("review/{travelId}/{travelName}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("travelId") ?: return@composable
            val travelName = backStackEntry.arguments?.getString("travelName") ?: return@composable

            ReviewWritingScreen(navController = navController, travelId = travelId, travelName = travelName)
        }
        composable("reviewList/{travelId}/{travelName}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("travelId") ?: return@composable
            val travelName = backStackEntry.arguments?.getString("travelName") ?: return@composable

            ReviewListScreen(navController = navController, travelId = travelId, travelName = travelName)
        }


        // Travel
        composable("scrapAndCart") {
            ScrapAndCartScreen(
                navController = navController
            )
        }
        composable("addTravel") {
            AddTravelDestinationScreen(navController = navController)
        }
        composable("recommendDestination/{title}/{city}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: return@composable
            val city = backStackEntry.arguments?.getString("city")
            TravelDestinationRecommendation(
                title = title,
                city = city,
                navController = navController
            )
        }
        composable("travelInfo/{id}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("id") ?: return@composable
            TravelInfoScreen(travelId = travelId, navController = navController)
        }


        // Team
        composable("teamList") {
            MyTeamListScreen(navController = navController)
        }
        composable("createTeam") {
            TeamNameCreateScreen(navController = navController)
        }
        composable("teamDetail/{teamId}") { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId") ?: return@composable
            TeamDetailScreen(navController = navController, teamId = teamId)
        }
        composable("joinTeam") {
            TeamJoinByCode(navController = navController)
        }


        // Search
        composable("search") {
            SearchScreen(navController = navController)
        }
        composable("searchReview") {
            SelectReviewWriteScreen(navController = navController)
        }

        // TBTI
        composable("tbtiTest") {
            TbtiTestScreen(navController)
        }
        composable("tbtiCodeInput") {
            val viewModel: TbtiCodeInputViewModel = hiltViewModel() // 또는 viewModel()
            TbtiCodeInputScreen(
                viewModel = viewModel
            )
        }

        composable("course") {
            MyTravelingCourseScreen(navController)
        }
        composable("myPage") {
            MyPageScreen(navController = navController)
        }
        composable("notification") {
            NotificationScreen(
                navController = navController,
                previousSelectedIndex = 0 // ?
            )
        }


        composable(
            route = "tbtiResult/{tbtiResultCode}/{rValue}/{eValue}/{nValue}/{aValue}",
            arguments = listOf(navArgument("tbtiResultCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val tbtiResultCode = backStackEntry.arguments?.getString("tbtiResultCode") ?: ""
            val rValue = backStackEntry.arguments?.getString("rValue") ?: "0"
            val eValue = backStackEntry.arguments?.getString("eValue") ?: "0"
            val nValue = backStackEntry.arguments?.getString("nValue") ?: "0"
            val aValue = backStackEntry.arguments?.getString("aValue") ?: "0"

            Log.d("valuesssss2", "r" + rValue.toString())
            Log.d("valuesssss2", "e" + eValue.toString())
            Log.d("valuesssss2", "n" + nValue.toString())
            Log.d("valuesssss2", "a" + aValue.toString())

            val traitScoresMap = mapOf(
                "R" to rValue.toInt(),
                "E" to eValue.toInt(),
                "N" to nValue.toInt(),
                "A" to aValue.toInt()
            )
            TbtiResultScreen(
                tbtiResultCode = tbtiResultCode,
                traitScores = traitScoresMap,
                navController = navController
            )
        }
    }
}

@HiltViewModel
class MyNavViewModel @Inject constructor(
    val tokenProvider: TokenProvider
) : ViewModel()