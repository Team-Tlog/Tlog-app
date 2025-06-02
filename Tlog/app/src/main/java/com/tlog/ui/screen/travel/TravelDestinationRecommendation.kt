package com.tlog.ui.screen.travel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.R
import com.tlog.ui.component.travel.CategorySelector
import com.tlog.ui.component.travel.DestinationCard
import com.tlog.ui.style.BodyTitle
import com.tlog.data.local.ScrapManager
import com.tlog.viewmodel.travel.TravelDestinationRecommendationViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.snapshotFlow

@Composable
fun TravelDestinationRecommendation(
    viewModel: TravelDestinationRecommendationViewModel = hiltViewModel(),
    title: String,
    city: String? = null,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val destinations by viewModel.destinations.collectAsState()

    val context = LocalContext.current

    val scrapList by ScrapManager.scrapList.collectAsState()


    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItemCount = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index == totalItemCount - 2 // 마지막에서 2번째 친구면
        }.collect { isLastItemVisible ->
            if (isLastItemVisible) {
                if (city != null)
                    viewModel.loadNextPage(city)
                else {
                    // 여기는 TBTI 여행 추천 호출하기
                }
            }

        }
    }





    LaunchedEffect(Unit) {
        viewModel.initUserIdAndScrapList(context)
        ScrapManager.init(context)
        if (city == null) {
            viewModel.loadDestinations()
        }
        else {
            viewModel.searchTravelToCity(city)
        }
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
                    //.verticalScroll(rememberScrollState())
            ) {
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
                    text = title,
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
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(destinations) { destination ->
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
