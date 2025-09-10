package com.tlog.ui.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
import com.tlog.ui.screen.share.ReportToDeveloperScreen
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
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TravelSearchScreen
import com.tlog.ui.screen.travel.TravelListScreen
import com.tlog.ui.screen.travel.TravelDetailScreen
import com.tlog.viewmodel.beginning.TbtiCodeInputViewModel
import com.tlog.viewmodel.beginning.LoginViewModel
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
        composable<Screen.ScrapAndCart> { ScrapAndCartScreen(navController = navController) }
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
            TeamDetailScreen(teamId = args.teamId)
        }
        composable<Screen.JoinTeam> { TeamJoinScreen(navController = navController) }

        // Search
        composable<Screen.Search> { TravelSearchScreen(navController = navController) }
        composable<Screen.SearchReview> { ReviewSearchScreen(navController = navController) }

        // TBTI
        composable<Screen.TbtiTest> { TbtiTestScreen(navController) }
        composable<Screen.TbtiCodeInput> {
            val viewModel: TbtiCodeInputViewModel = hiltViewModel()
            TbtiCodeInputScreen(viewModel = viewModel)
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
        composable<Screen.Course> { MyTravelingCourseScreen(navController) }
        composable<Screen.Notification> {
            NotificationScreen(navController = navController, previousSelectedIndex = 0)
        }
    }
}

@HiltViewModel
class MyNavViewModel @Inject constructor(
    val tokenProvider: TokenProvider
) : ViewModel()