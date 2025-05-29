package com.tlog.ui.screen.travel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tlog.R
import com.tlog.ui.component.travel.CategorySelector
import com.tlog.ui.component.travel.DestinationCard
import com.tlog.ui.style.BodyTitle
import com.tlog.data.local.ScrapManager
import com.tlog.viewmodel.travel.TravelDestinationRecommendationViewModel

@Composable
fun TravelDestinationRecommendation(
    viewModel: TravelDestinationRecommendationViewModel = hiltViewModel(),
    navController: NavHostController) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val destinations by viewModel.destinations.collectAsState()
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    // Observe scrapList from ScrapManager's StateFlow
    val scrapList by ScrapManager.scrapList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initUserId(context)
        ScrapManager.init(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Header with Logo and Icons
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color.LightGray)
                        )  //여기에 로고 생기면 추가해야함

                        Row {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(8.dp)
                                    .clickable {
                                        //추후 로직 구현
                                        Log.d("검색", "검색아이콘 눌림")
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "검색",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(8.dp)
                                    .clickable {
                                        //추후 로직 구현
                                        Log.d("알림창", "알림아이콘 눌림")
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_notification),
                                    contentDescription = "알림",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(35.dp))

                Text(
                    text = "000의 여행지 추천",
                    style = BodyTitle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(33.dp))

                // Category Selector
                CategorySelector(
                    categories = listOf("추천순", "인기순", "리뷰순"),
                    selectedCategory = selectedCategory,
                    onCategorySelected = { viewModel.setCategory(it) }
                )

                Spacer(modifier = Modifier.height(19.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    destinations.forEach { destination ->
                        val isFavorite = scrapList.contains(destination.id)
                        DestinationCard(
                            destination = destination,
                            isFavorite = isFavorite,
                            onFavoriteToggle = {
                                viewModel.toggleScrap(context, destination.id)
                            },
                            onClick = {
                                navController.navigate("travelInfo/${destination.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}
