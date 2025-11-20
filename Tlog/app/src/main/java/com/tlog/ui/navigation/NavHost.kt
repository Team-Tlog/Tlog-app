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
import androidx.navigation.toRoute
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tlog.api.retrofit.TokenProvider
import com.tlog.ui.screen.beginning.ChooseMyTypeDestinationScreen
import com.tlog.ui.screen.beginning.LoginScreen
import com.tlog.ui.screen.beginning.TbtiCodeInputScreen
import com.tlog.ui.screen.beginning.TbtiIntroScreen
import com.tlog.ui.screen.beginning.TbtiResultScreen
import com.tlog.ui.screen.beginning.TbtiTestScreen
import com.tlog.ui.screen.review.AddTravelScreen
import com.tlog.ui.screen.review.ReviewListScreen
import com.tlog.ui.screen.review.ReviewWriteScreen
import com.tlog.ui.screen.review.ReviewSearchScreen
import com.tlog.ui.screen.share.BannerDetailScreen
import com.tlog.ui.screen.share.FeedbackScreen
import com.tlog.ui.screen.share.ScrapAndCartScreen
import com.tlog.ui.screen.share.MainScreen
import com.tlog.ui.screen.share.MapScreen
import com.tlog.ui.screen.share.MyPageScreen
import com.tlog.ui.screen.share.NotificationScreen
import com.tlog.ui.screen.share.ReportToDeveloperScreen
import com.tlog.ui.screen.share.RestaurantScreen
import com.tlog.ui.screen.sns.ChatListScreen
import com.tlog.ui.screen.sns.SNSChattingScreen
import com.tlog.ui.screen.sns.SnsIdCreateScreen
import com.tlog.ui.screen.sns.SnsScreen
import com.tlog.ui.screen.sns.SnsDetailScreen
import com.tlog.ui.screen.sns.SnsProfileScreen
import com.tlog.ui.screen.sns.SnsPostWriteDetailScreen
import com.tlog.ui.screen.sns.SnsSearchScreen
import com.tlog.ui.screen.team.MyTeamListScreen
import com.tlog.ui.screen.team.TeamDetailScreen
import com.tlog.ui.screen.team.TeamJoinScreen
import com.tlog.ui.screen.team.TeamCreateScreen
import com.tlog.ui.screen.team.TeamInfoInputScreen
import com.tlog.ui.screen.travel.AiCourseSelectCartScreen
import com.tlog.ui.screen.travel.AiRecommendCourseResultScreen
import com.tlog.ui.screen.travel.CourseInputScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TravelSearchScreen
import com.tlog.ui.screen.travel.TravelListScreen
import com.tlog.ui.screen.travel.TravelDetailScreen
import com.tlog.viewmodel.beginning.LoginViewModel
import com.tlog.viewmodel.travel.CourseSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@Composable
fun NavHost(
    navController: NavHostController,
    startScreen: Screen,
    loginViewModel: LoginViewModel,
    launcher: ActivityResultLauncher<Intent>,
    googleSignInClient: GoogleSignInClient
) {
    val viewModel: MyNavViewModel = hiltViewModel() // 고민 좀 해볼건데 일단 이렇게
    val sharedCourseViewModel: CourseSharedViewModel = hiltViewModel()



    NavHost(navController = navController, startDestination = startScreen) {
        // TypeSafety
        composable<Screen.Main> {
            MainScreen(navController = navController)
        }
        composable<Screen.Login> {
            LoginScreen(
                viewModel = loginViewModel,
                onGoogleLoginClick = {
                    val intent = googleSignInClient.signInIntent
                    launcher.launch(intent)
                },
                navController = navController
            )
        }
        composable<Screen.Map> { MapScreen() }

        composable<Screen.SelectTravel> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.SelectTravel>()
            ChooseMyTypeDestinationScreen(tbtiValue = args.tbtiValue, navController = navController)
        }

        composable<Screen.Restaurant> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Restaurant>()

            RestaurantScreen(latitude = args.latitude.toDouble(), longitude = args.longitude.toDouble())
        }

        composable<Screen.BannerDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.BannerDetail>()

            BannerDetailScreen(bannerId = args.bannerId, navController = navController)
        }

        // SNS
        composable<Screen.SnsMain> { SnsScreen(navController = navController) }
        composable<Screen.SnsId> { SnsIdCreateScreen(navController = navController) }
        composable<Screen.Sns> {
            val snsId = viewModel.tokenProvider.getSnsId()

            if (snsId.isNullOrEmpty())
                SnsIdCreateScreen(navController = navController)
            else
                SnsScreen(navController = navController)
        }
        composable<Screen.SnsPostDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.SnsPostDetail>()
            SnsDetailScreen(postId = args.postId, navController = navController)
        }
        composable<Screen.SnsSearch> { SnsSearchScreen(navController = navController) }
        composable<Screen.SnsMyPage> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.SnsMyPage>()
            SnsProfileScreen(navController = navController, userId = args.userId)
        }
        composable<Screen.SnsPostWrite> { SnsPostWriteDetailScreen() }

        // Review
        composable<Screen.ReviewWrite> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.ReviewWrite>()

            ReviewWriteScreen(navController = navController, travelId = args.travelId, travelName = args.travelName)
        }
        composable<Screen.ReviewList> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.ReviewList>()
            ReviewListScreen(navController = navController, travelId = args.travelId, travelName = args.travelName)
        }

        // Travel
        composable<Screen.ScrapAndCart> {
            ScrapAndCartScreen(
                navController = navController,
                sharedViewModel = sharedCourseViewModel
            )
        }
        composable<Screen.AddTravel> { AddTravelScreen(navController = navController) }
        composable<Screen.TravelList> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.TravelList>()
            TravelListScreen(title = args.title, city = args.city, navController = navController)
        }
        composable<Screen.TravelInfo> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.TravelInfo>()
            TravelDetailScreen(travelId = args.travelId, navController = navController)
        }

        // Team
        composable<Screen.TeamList> { MyTeamListScreen(navController = navController) }
        composable<Screen.CreateTeam> { TeamCreateScreen(navController = navController) }
        composable<Screen.TeamInfoInput> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.TeamInfoInput>()
            TeamInfoInputScreen(teamName = args.teamName, navController = navController)
        }
        composable<Screen.TeamDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.TeamDetail>()
            TeamDetailScreen(teamId = args.teamId, navController = navController, sharedViewModel = sharedCourseViewModel)
        }
        composable<Screen.JoinTeam> { TeamJoinScreen(navController = navController) }

        // Search
        composable<Screen.Search> { TravelSearchScreen(navController = navController) }
        composable<Screen.SearchReview> { ReviewSearchScreen(navController = navController) }

        // TBTI
        composable<Screen.TbtiTest> { TbtiTestScreen(navController) }
        composable<Screen.TbtiCodeInput> {
            TbtiCodeInputScreen(navController = navController)
        }
        composable<Screen.TbtiIntro> { TbtiIntroScreen(navController = navController) }
        composable<Screen.TbtiResult> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.TbtiResult>()
            val resultList = listOf(args.sValue, args.eValue, args.lValue, args.aValue)
            var resultCode = ""
            resultList.forEach { result ->
                resultCode += if (result.length == 1) "0${result}" else result
            }
            val traitScoresMap = mapOf(
                "R" to args.sValue.toInt(),
                "E" to args.eValue.toInt(),
                "N" to args.lValue.toInt(),
                "A" to args.aValue.toInt()
            )
            TbtiResultScreen(
                tbtiResult = args.tbtiResultCode,
                tbtiResultCode = resultCode,
                traitScores = traitScoresMap,
                navController = navController
            )
        }

        // MyPage
        composable<Screen.MyPage> { MyPageScreen(navController = navController) }
        composable<Screen.Report> { ReportToDeveloperScreen() }
        composable<Screen.Course> { MyTravelingCourseScreen(navController = navController) }
        composable<Screen.Feedback> { FeedbackScreen(navController = navController) }
        composable<Screen.Course> { MyTravelingCourseScreen(navController = navController) }
        composable<Screen.Notification> {
            NotificationScreen(navController = navController)
        }

        // Chatting
        composable("chatList") {
            ChatListScreen(navController = navController)
        }
        composable(
            route = "chatting/{chatRoomId}?teamName={teamName}&membersJson={membersJson}",
            arguments = listOf(
                navArgument("chatRoomId") { type = NavType.StringType },
                navArgument("teamName") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("membersJson") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val chatRoomId = backStackEntry.arguments?.getString("chatRoomId")?.toLongOrNull() ?: 0L
            val teamName = backStackEntry.arguments?.getString("teamName") ?: "Team"
            val membersJson = backStackEntry.arguments?.getString("membersJson")

            // JSON 파싱
            val members: List<com.tlog.viewmodel.sns.MemberProfile> = try {
                if (membersJson != null && membersJson != "null") {
                    val type = object : TypeToken<List<com.tlog.viewmodel.sns.MemberProfile>>() {}.type
                    Gson().fromJson(membersJson, type) ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("NavHost", "Error parsing members JSON", e)
                emptyList()
            }

            SNSChattingScreen(
                chatRoomId = chatRoomId,
                teamName = teamName,
                members = members
            )
        }


        // AI
        composable<Screen.AiCourseSelectCart> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.AiCourseSelectCart>()

            AiCourseSelectCartScreen(
                navController = navController,
                isTeam = args.isTeam,
                sharedViewModel = sharedCourseViewModel
            )
        }

        composable<Screen.AiCourseInput> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.AiCourseInput>()

            CourseInputScreen(
                navController = navController,
                isTeam = args.isTeam,
                sharedViewModel = sharedCourseViewModel
            )
        }

        composable<Screen.AiCourseResult> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.AiCourseResult>()

            AiRecommendCourseResultScreen(
                sharedViewModel = sharedCourseViewModel,
                isTeam = args.isTeam,
                navController = navController
            )
        }
    }
}

@HiltViewModel
class MyNavViewModel @Inject constructor(
    val tokenProvider: TokenProvider
) : ViewModel()