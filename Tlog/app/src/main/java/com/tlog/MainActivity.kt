package com.tlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tlog.ui.screen.travel.AiRecommendCourseResultScreen
import com.tlog.ui.screen.travel.TravelInfoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
            TravelInfoScreen()
        }
    }
}
