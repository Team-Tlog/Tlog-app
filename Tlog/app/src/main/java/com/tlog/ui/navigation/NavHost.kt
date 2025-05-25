package com.tlog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tlog.data.repository.CartRepository
import com.tlog.ui.screen.review.AddTravelDestinationScreen
import com.tlog.ui.screen.review.ReviewWritingScreen
import com.tlog.ui.screen.review.SelectReviewWriteScreen
import com.tlog.ui.screen.share.CartScreen
import com.tlog.ui.screen.share.MainScreen
import com.tlog.ui.screen.share.NotificationScreen
import com.tlog.ui.screen.sns.SnsPostWriteDetailScreen
import com.tlog.ui.screen.team.MyTeamListScreen
import com.tlog.ui.screen.team.TeamNameCreateScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TeamTravelingCourseScreen
import com.tlog.viewmodel.share.CartViewModel
import com.tlog.viewmodel.share.MainViewModel

@Composable
fun NavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            // 메인화면으로 수정해야함
            MainScreen(navController = navController)
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
            AddTravelDestinationScreen()
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
        composable("searchReview") {
            SelectReviewWriteScreen(navController = navController)
        }

    }
}
