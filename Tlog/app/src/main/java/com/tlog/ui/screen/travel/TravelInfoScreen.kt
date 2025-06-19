package com.tlog.ui.screen.travel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.travel.review.ReviewSection
import com.tlog.ui.component.travel.SimilarTravelSection
import com.tlog.ui.component.travel.TravelInfoSummary
import com.tlog.ui.component.travel.TravelTopImageBox
import com.tlog.viewmodel.travel.TravelInfoViewModel
import kotlin.math.floor

@Composable
fun TravelInfoScreen(
    travelId: String,
    viewModel: TravelInfoViewModel = hiltViewModel(),
    navController: NavController
) {
    val detail = viewModel.destinationDetail.collectAsState().value

    // 여행지 ID로 API 호출 (최초 1회)
    LaunchedEffect(travelId) {
        viewModel.loadDestinationById(travelId)
        Log.d("okhttp", "TravelInfoScreen: ${detail.toString()}")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        detail?.let { destination ->
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

                            Spacer(modifier = Modifier.height(89.dp))

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
                                navController.navigate("reviewList/$travelId/${destination.name}")
                            },
                            reviewWrite = {
                                navController.navigate("review/$travelId/${destination.name}")
                            },
                            onClick = { userId ->
                                navController.navigate("snsMyPage/$userId")
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
                                    navController.navigate("travelInfo/$travelId")
                                }
                            )
                        }
                    }
                }
            }
        } ?: run {
            // 로딩 또는 데이터 없음 처리
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
            ) {
                Text("여행지를 불러오는 중입니다...")
            }
        }
    }
}
