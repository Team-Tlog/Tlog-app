package com.tlog.ui.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.tlog.data.repository.CartRepository
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.ui.screen.review.AddTravelDestinationScreen
import com.tlog.ui.screen.review.ReviewWritingScreen
import com.tlog.ui.screen.review.SelectReviewWriteScreen
import com.tlog.ui.screen.share.CartScreen
import com.tlog.ui.screen.share.MainScreen
import com.tlog.ui.screen.sns.SnsPostWriteDetailScreen
import com.tlog.ui.screen.team.MyTeamListScreen
import com.tlog.ui.screen.team.TeamNameCreateScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TeamTravelingCourseScreen
import com.tlog.ui.screen.travel.TravelDestinationRecommendation
import com.tlog.ui.screen.travel.TravelInfoScreen
import com.tlog.viewmodel.beginning.login.LoginViewModel
import com.tlog.viewmodel.share.CartViewModel
import com.tlog.viewmodel.share.MainViewModel

@Composable
fun NavHost(
    navController: NavHostController,
    startScreen: String,
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    launcher: ActivityResultLauncher<Intent>,
    googleSignInClient: GoogleSignInClient
) {
    NavHost(navController = navController, startDestination = "searchReview") { //startScreen) {
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
            // 장바구니?화면으로 수정해야함
            MyTravelingCourseScreen(navController)
        }
        composable("mypage") {
            // 마이페이지로 수정해야함
            MyTravelingCourseScreen(navController)
        }
        composable("cart") {
            val factory = object : CartViewModel.AssistedFactory {
                override fun create(userId: String): CartViewModel {
                    return CartViewModel(
                        repository = CartRepository(),
                        userId = mainViewModel.userId.value!!
                    )
                }
            }

            val cartViewModel: CartViewModel = viewModel(
                factory = CartViewModel.provideFactory(factory, mainViewModel.userId.value!!)
            )
            CartScreen(
                viewModel = cartViewModel,
                navController = navController
            )
        }
        composable("addTravel") {
            AddTravelDestinationScreen(navController = navController)
        }
        composable("post2") {
            SnsPostWriteDetailScreen()
        }
        composable("review") {
            ReviewWritingScreen(navController = navController)
        }
        composable("teamList") {
            MyTeamListScreen(navController = navController)
        }
        composable("createTeam") {
            TeamNameCreateScreen(navController = navController)
        }
        composable("recommendDestination") {
            TravelDestinationRecommendation(navController = navController)
        }
        composable("searchReview") {
            SelectReviewWriteScreen(navController = navController)
        }
        composable("travelInfo/{id}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("id") ?: return@composable
            TravelInfoScreen(id = travelId)
        }
    }
}
