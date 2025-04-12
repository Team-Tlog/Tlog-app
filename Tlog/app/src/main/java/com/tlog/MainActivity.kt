package com.tlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tlog.ui.navigation.NavHost
import com.tlog.ui.screen.review.ReviewWritingScreen
import com.tlog.ui.screen.share.CartScreen
import com.tlog.ui.screen.share.NotificationScreen
import com.tlog.ui.screen.travel.AiRecommendCourseResultScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.sns.SNSIdCreateScreen
import com.tlog.ui.screen.share.MyPageScreen
import com.tlog.viewmodel.share.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()


            //Bottom Bar를 사용한 화면 전환을 위한 컨트롤러
            val navController = rememberNavController()
            NavHost(navController = navController)

            CartScreen()
        }
    }
}
