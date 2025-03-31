package com.tlog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TeamTravelingCourseScreen

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            // 메인화면으로 수정해야함
            MyTravelingCourseScreen(navController)
        }
        composable("course") {
            //TeamTravelingCourse는 어떻게?
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
    }
}
