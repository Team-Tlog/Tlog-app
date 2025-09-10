package com.tlog.ui.screen.travel

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.travel.review.ReviewSection
import com.tlog.ui.component.travel.SimilarTravelSection
import com.tlog.ui.component.travel.TravelInfoSummary
import com.tlog.ui.component.travel.TravelTopImageBox
import com.tlog.viewmodel.travel.TravelInfoViewModel
import com.tlog.viewmodel.travel.TravelInfoViewModel.UiEvent
import kotlin.math.floor

@Composable
fun TravelDetailScreen(
    travelId: String,
    viewModel: TravelInfoViewModel = hiltViewModel(),
    navController: NavController
) {
    val travel = viewModel.destinationDetail.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) { popUpTo(navController.graph.id) { inclusive = true } }
                        // 시밀러 여행지 눌렀을 때 재사용되는 상황 제거하기 위해 주석
//                        launchSingleTop = false
//                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    LaunchedEffect(travelId) {
        viewModel.getTravelInfo(travelId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        travel?.let { destination ->
            Column {
                TravelTopImageBox(
                    imageUrl = destination.imageUrl,
                    isScrap = viewModel.isScraped(travelId),
                    clickScrap = {
                        viewModel.toggleScrap(travelId)
                    }
                )

                Box(
                    modifier = Modifier
                        .offset(y = (-79).dp)
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                        )
                        .padding(top = 30.dp)
                ) {
                    Column {
                        Column(
                            modifier = Modifier.padding(horizontal = 31.5.dp)
                        ) {
                            TravelInfoSummary(destination)

                            Spacer(modifier = Modifier.height(51.dp))

                            HorizontalDivider(
                                color = Color(0xFFE3E3E3),
                                thickness = 1.dp
                            )
                        }

                        Spacer(modifier = Modifier.height(29.dp))

                        ReviewSection(
                            avgStarRating = floor(destination.averageRating * 100) / 100, // 소수점 2자리까지 절삭
                            ratingDistribution = destination.ratingDistribution,
                            reviewList = destination.top2Reviews,
                            reviewCnt = destination.reviewCount,
                            moreReview = {
                                viewModel.navToReviewList(travelId, destination.name)
                            },
                            reviewWrite = {
                                viewModel.navToReviewWrite(travelId, destination.name)
                            },
                            onClick = { userId ->
                                viewModel.navToSnsMyPage(userId)
                            }
                        )

                        Spacer(modifier = Modifier.height(48.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 31.5.dp)
                        ) {
                            SimilarTravelSection(
                                travelList = destination.relatedDestinations,
                                clickable = { travelId ->
                                    viewModel.navToTravelInfo(travelId)
                                }
                            )
                        }
                    }
                }
            }
        } ?: run {
            // api 로딩 중일 때
        }
    }
}
