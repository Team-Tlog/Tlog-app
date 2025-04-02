package com.tlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.tlog.ui.navigation.NavHost
import com.tlog.ui.screen.travel.AiRecommendCourseResultScreen
import com.tlog.ui.screen.travel.TravelInfoScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Bottom Bar를 사용한 화면 전환을 위한 컨트롤러
            val navController = rememberNavController()
            NavHost(navController = navController)

            //TbtiTestScreen(1, 10)
            //LoginScreen()
            //TbtiIntroScreen()
            //TbtiCodeInputScreen()
            //UserInfoInputScreen()
            //TbtiResultScreen()
            //AddTravelDestinationScreen()
            //SelectReviewWriteScreen()
            //ReviewWritingScreen()
            //ChooseMyTypeDestinationScreen()
            //CartScreen()
            //TeamNameCreateScreen()
            //AiCourseSelectCartScreen()
            //MyTeamListScreen()
            //TeamDetailScreen()
            //CourseInputScreen()
            //AiRecommendCourseResultScreen()
            //MyTravelingCourseScreen(navController)
            //TeamTravelingCourseScreen(navController)
            TravelInfoScreen()
        }
    }
}
