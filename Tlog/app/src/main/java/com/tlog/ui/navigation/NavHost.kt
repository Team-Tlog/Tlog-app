package com.tlog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tlog.ui.screen.share.CartScreen
import com.tlog.ui.screen.share.NotificationScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TeamTravelingCourseScreen
import com.tlog.viewmodel.share.CartViewModel
import com.tlog.viewmodel.share.CartViewModelFactory

@Composable
fun NavHost(
    navController: NavHostController,
    userId: String
) {
    NavHost(navController = navController, startDestination = "cart") {
        composable("main") {
            // 메인화면으로 수정해야함
            NotificationScreen(navController, previousSelectedIndex = 0)
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
            // 받아온 userId 넣기
            val cartViewModel: CartViewModel = viewModel(
                factory = CartViewModelFactory(userId)
            )
            CartScreen(
                viewModel = cartViewModel,
                navController = navController
            )
        }

    }
}
