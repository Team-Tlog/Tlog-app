package com.tlog.ui.navigation

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.navigation.navArgument
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
import com.tlog.ui.screen.team.TeamJoinByCode
import com.tlog.ui.screen.team.TeamCreateScreen
import com.tlog.ui.screen.team.TeamInfoInputScreen
import com.tlog.ui.screen.travel.AiCourseSelectCartScreen
import com.tlog.ui.screen.travel.AiRecommendCourseResultScreen
import com.tlog.ui.screen.travel.MyTravelingCourseScreen
import com.tlog.ui.screen.travel.TravelSearchScreen
import com.tlog.ui.screen.travel.TravelListScreen
import com.tlog.ui.screen.travel.TravelDetailScreen
import com.tlog.viewmodel.beginning.TbtiCodeInputViewModel
import com.tlog.viewmodel.beginning.LoginViewModel
import com.tlog.viewmodel.tmp.TmpCartViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@Composable
fun NavHost(
    navController: NavHostController,
    startScreen: String,
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
            Log.d("sns", "snsId: ${snsId}")
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
        composable<Screen.JoinTeam> { TeamJoinByCode(navController = navController) }

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





        // Main
        composable("main") {
            MainScreen(navController = navController)
        }


        // Login
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

        // Map (지도에서 보기)
        composable("map") {
            MapScreen()
        }


        //SNS
        composable("snsMain") {
            SnsScreen(navController = navController)
        }
        composable("snsId") {
            SnsIdCreateScreen(navController = navController)
        }
        composable("sns") {
            val snsId = viewModel.tokenProvider.getSnsId()
            Log.d("sns", "snsId: ${snsId}")
            if (snsId == null || snsId.isEmpty())
                SnsIdCreateScreen(navController = navController)
            else
                SnsScreen(navController = navController)
        }
        composable("snsPostDetail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: return@composable
            SnsDetailScreen(postId = postId, navController = navController)
        }
        composable("snsSearch") {
            SnsSearchScreen(navController = navController)
        }
        composable("snsMyPage/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable

            SnsProfileScreen(
                navController = navController,
                userId = userId
            )
        }



        composable("post2") {
            SnsPostWriteDetailScreen()
        }

        // Review
        composable("review/{travelId}/{travelName}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("travelId") ?: return@composable
            val travelName = backStackEntry.arguments?.getString("travelName") ?: return@composable

            ReviewWriteScreen(navController = navController, travelId = travelId, travelName = travelName)
        }
        composable("reviewList/{travelId}/{travelName}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("travelId") ?: return@composable
            val travelName = backStackEntry.arguments?.getString("travelName") ?: return@composable

            ReviewListScreen(navController = navController, travelId = travelId, travelName = travelName)
        }


        // Travel
        composable("scrapAndCart") {
            ScrapAndCartScreen(
                navController = navController
            )
        }
        composable("addTravel") {
            AddTravelScreen(navController = navController)
        }
        composable("recommendDestination/{title}/{city}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: return@composable
            val city = backStackEntry.arguments?.getString("city")
            TravelListScreen(
                title = title,
                city = city,
                navController = navController
            )
        }
        composable("travelInfo/{id}") { backStackEntry ->
            val travelId = backStackEntry.arguments?.getString("id") ?: return@composable
            TravelDetailScreen(travelId = travelId, navController = navController)
        }


        // Team
        composable("teamList") {
            MyTeamListScreen(navController = navController)
        }
        composable("createTeam") {
            TeamCreateScreen(navController = navController)
        }
        composable("teamInfoInput/{teamName}") { backstackEntry ->
            val teamName = backstackEntry.arguments?.getString("teamName") ?: return@composable
            TeamInfoInputScreen(
                teamName = teamName,
                navController = navController
            )

        }
        composable("teamDetail/{teamId}") { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId") ?: return@composable
            TeamDetailScreen(teamId = teamId)
        }
        composable("joinTeam") {
            TeamJoinByCode(navController = navController)
        }


        // Search
        composable("search") {
            TravelSearchScreen(navController = navController)
        }
        composable("searchReview") {
            ReviewSearchScreen(navController = navController)
        }

        // TBTI
        composable("tbtiTest") {
            TbtiTestScreen(navController)
        }
        composable("tbtiCodeInput") {
            val viewModel: TbtiCodeInputViewModel = hiltViewModel() // 또는 viewModel()
            TbtiCodeInputScreen(
                viewModel = viewModel
            )
        }
        composable(route = "tbtiIntro") {
            TbtiIntroScreen(navController = navController)
        }
        composable(
            route = "tbtiResult/{tbtiResultCode}/{sValue}/{eValue}/{lValue}/{aValue}",
            arguments = listOf(navArgument("tbtiResultCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val tbtiResultCode = backStackEntry.arguments?.getString("tbtiResultCode") ?: ""
            val rValue = backStackEntry.arguments?.getString("sValue") ?: "00"
            val eValue = backStackEntry.arguments?.getString("eValue") ?: "00"
            val nValue = backStackEntry.arguments?.getString("lValue") ?: "00"
            val aValue = backStackEntry.arguments?.getString("aValue") ?: "00"
            val resultList = listOf(rValue, eValue, nValue, aValue)
            var resultCode = ""

            resultList.forEach { result ->
                if (result.length == 1)
                    resultCode += "0$result"
                else
                    resultCode += result
            }

            Log.d("valuesssss2", "r" + rValue.toString())
            Log.d("valuesssss2", "e" + eValue.toString())
            Log.d("valuesssss2", "n" + nValue.toString())
            Log.d("valuesssss2", "a" + aValue.toString())

            val traitScoresMap = mapOf(
                "R" to rValue.toInt(),
                "E" to eValue.toInt(),
                "N" to nValue.toInt(),
                "A" to aValue.toInt()
            )
            TbtiResultScreen(
                tbtiResult = tbtiResultCode,
                tbtiResultCode = resultCode,
                traitScores = traitScoresMap,
                navController = navController
            )
        }

        //MyPage
        composable("myPage") {
            MyPageScreen(navController = navController)
        }
        composable("report") {
            ReportToDeveloperScreen()
        }

        composable("course") {
            MyTravelingCourseScreen(navController)
        }

        composable("notification") {
            NotificationScreen(
                navController = navController,
                previousSelectedIndex = 0 // ?
            )
        }

        composable("AiSelect") {
            val viewModel: TmpCartViewModel = hiltViewModel()
            AiCourseSelectCartScreen(viewModel = viewModel)
        }

        composable("AiRecommend") {
            val viewModel: TmpCartViewModel = hiltViewModel()
            AiRecommendCourseResultScreen(viewModel = viewModel)
        }

    }
}

@HiltViewModel
class MyNavViewModel @Inject constructor(
    val tokenProvider: TokenProvider
) : ViewModel()