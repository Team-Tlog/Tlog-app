package com.tlog.ui.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.ui.screen.beginning.TbtiCodeInputScreen
import com.tlog.ui.screen.beginning.TbtiTestScreen
import com.tlog.ui.screen.review.AddTravelDestinationScreen
import com.tlog.ui.screen.review.ReviewListScreen
import com.tlog.ui.screen.review.ReviewWritingScreen
import com.tlog.ui.screen.review.SelectReviewWriteScreen
import com.tlog.ui.screen.share.ScrapAndCartScreen
import com.tlog.ui.screen.share.MainScreen
import com.tlog.ui.screen.share.MyPageScreen
import com.tlog.ui.screen.sns.SnsPostWriteDetailScreen
import com.tlog.ui.screen.team.MyTeamListScreen
import com.tlog.ui.screen.team.TeamDetailScreen
import com.tlog.ui.screen.team.TeamJoinByCode
import com.tlog.ui.screen.team.TeamNameCreateScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.SearchScreen
import com.tlog.ui.screen.travel.TeamTravelingCourseScreen
import com.tlog.ui.screen.travel.TravelDestinationRecommendation
import com.tlog.ui.screen.travel.TravelInfoScreen
import com.tlog.viewmodel.beginning.TbtiCodeInputViewModel
import com.tlog.viewmodel.beginning.login.LoginViewModel

@Composable
fun NavHost(
    navController: NavHostController,
    startScreen: String,
    loginViewModel: LoginViewModel,
    launcher: ActivityResultLauncher<Intent>,
    googleSignInClient: GoogleSignInClient
) {

    NavHost(navController = navController, startDestination = startScreen) {
        composable("main") {
            MainScreen(navController = navController)
        }
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
        composable("course") {
            //MyTravelingCourse는 어떻게?
            TeamTravelingCourseScreen(navController)
        }
        composable("sns") {
            // SNS로 수정 필요
            MyTravelingCourseScreen(navController)
        }
        composable("mypage") {
            MyPageScreen(navController = navController)
        }
        composable("scrapAndCart") {
            ScrapAndCartScreen(
                navController = navController
            )
        }
        composable("addTravel") {
            AddTravelDestinationScreen(navController = navController)
        }
        composable("post2") {
            SnsPostWriteDetailScreen()
        }
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


        // team
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



        composable("recommendDestination/{title}/{city}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: return@composable
            val city = backStackEntry.arguments?.getString("city")
            TravelDestinationRecommendation(
                title = title,
                city = city,
                navController = navController
            )
        }
        composable("searchReview") {
            SelectReviewWriteScreen(navController = navController)
        }
        composable("travelInfo/{id}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("id") ?: return@composable
            TravelInfoScreen(travelId = travelId, navController = navController)
        }
        composable("tbtiTest") {
            TbtiTestScreen()
        }
        composable("tbtiCodeInput") {
            val viewModel: TbtiCodeInputViewModel = hiltViewModel() // 또는 viewModel()
            TbtiCodeInputScreen(
                viewModel = viewModel
            )
        }
        composable("search") {
            SearchScreen(navController = navController)
        }

    }
}
