package com.tlog.ui.navigation

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.tlog.api.retrofit.TokenProvider
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.ui.screen.beginning.TbtiCodeInputScreen
import com.tlog.ui.screen.beginning.TbtiIntroScreen
import com.tlog.ui.screen.beginning.TbtiResultScreen
import com.tlog.ui.screen.beginning.TbtiTestScreen
import com.tlog.ui.screen.review.AddTravelScreen
import com.tlog.ui.screen.review.ReviewListScreen
import com.tlog.ui.screen.review.ReviewWriteScreen
import com.tlog.ui.screen.review.ReviewSearchScreen
import com.tlog.ui.screen.share.ScrapAndCartScreen
import com.tlog.ui.screen.share.MainScreen
import com.tlog.ui.screen.share.MapScreen
import com.tlog.ui.screen.share.MyPageScreen
import com.tlog.ui.screen.share.NotificationScreen
import com.tlog.ui.screen.sns.SnsIdCreateScreen
import com.tlog.ui.screen.sns.SnsScreen
import com.tlog.ui.screen.sns.SnsDetailScreen
import com.tlog.ui.screen.sns.SnsProfileScreen
import com.tlog.ui.screen.sns.SnsPostWriteDetailScreen
import com.tlog.ui.screen.sns.SnsSearchScreen
import com.tlog.ui.screen.team.MyTeamListScreen
import com.tlog.ui.screen.team.TeamDetailScreen
import com.tlog.ui.screen.team.TeamJoinByCode
import com.tlog.ui.screen.team.TeamCreateScreen
import com.tlog.ui.screen.travel.AiCourseSelectCartScreen
import com.tlog.ui.screen.travel.AiRecommendCourseResultScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TravelSearchScreen
import com.tlog.ui.screen.travel.TravelListScreen
import com.tlog.ui.screen.travel.TravelDetailScreen
import com.tlog.viewmodel.beginning.TbtiCodeInputViewModel
import com.tlog.viewmodel.beginning.login.LoginViewModel
import com.tlog.viewmodel.tmp.TmpCartViewModel
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


    NavHost(navController = navController, startDestination = "AiRecommend") {
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
            SnsScreen(navController = navController)
        }
        composable("snsId") {
            SnsIdCreateScreen(navController = navController)
        }
        composable("sns") {
            val snsId = viewModel.tokenProvider.getSnsId()
            Log.d("sns", "snsId: ${snsId}")
            if (snsId == null || snsId.isEmpty())
                SnsIdCreateScreen(navController = navController)
            else
                SnsScreen(navController = navController)
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

            SnsProfileScreen(
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

            ReviewWriteScreen(navController = navController, travelId = travelId, travelName = travelName)
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
            AddTravelScreen(navController = navController)
        }
        composable("recommendDestination/{title}/{city}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: return@composable
            val city = backStackEntry.arguments?.getString("city")
            TravelListScreen(
                title = title,
                city = city,
                navController = navController
            )
        }
        composable("travelInfo/{id}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("id") ?: return@composable
            TravelDetailScreen(travelId = travelId, navController = navController)
        }


        // Team
        composable("teamList") {
            MyTeamListScreen(navController = navController)
        }
        composable("createTeam") {
            TeamCreateScreen(navController = navController)
        }
        composable("teamDetail/{teamId}") { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId") ?: return@composable
            TeamDetailScreen(teamId = teamId)
        }
        composable("joinTeam") {
            TeamJoinByCode(navController = navController)
        }


        // Search
        composable("search") {
            TravelSearchScreen(navController = navController)
        }
        composable("searchReview") {
            ReviewSearchScreen(navController = navController)
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
        composable(route = "tbtiIntro") {
            TbtiIntroScreen(navController = navController)
        }
        composable(
            route = "tbtiResult/{tbtiResultCode}/{sValue}/{eValue}/{lValue}/{aValue}",
            arguments = listOf(navArgument("tbtiResultCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val tbtiResultCode = backStackEntry.arguments?.getString("tbtiResultCode") ?: ""
            val rValue = backStackEntry.arguments?.getString("sValue") ?: "00"
            val eValue = backStackEntry.arguments?.getString("eValue") ?: "00"
            val nValue = backStackEntry.arguments?.getString("lValue") ?: "00"
            val aValue = backStackEntry.arguments?.getString("aValue") ?: "00"
            val resultList = listOf(rValue, eValue, nValue, aValue)
            var resultCode = ""

            resultList.forEach { result ->
                if (result.length == 1)
                    resultCode += "0$result"
                else
                    resultCode += result
            }

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
                tbtiResult = tbtiResultCode,
                tbtiResultCode = resultCode,
                traitScores = traitScoresMap,
                navController = navController
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

        composable("AiSelect") {
            val viewModel: TmpCartViewModel = hiltViewModel()
            AiCourseSelectCartScreen(viewModel = viewModel)
        }

        composable("AiRecommend") {
            val viewModel: TmpCartViewModel = hiltViewModel()
            AiRecommendCourseResultScreen(viewModel = viewModel)
        }

    }
}

@HiltViewModel
class MyNavViewModel @Inject constructor(
    val tokenProvider: TokenProvider
) : ViewModel()