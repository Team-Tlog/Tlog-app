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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.R
import com.tlog.ui.component.travel.CategorySelector
import com.tlog.ui.component.travel.DestinationCard
import com.tlog.ui.style.BodyTitle
import com.tlog.viewmodel.travel.TravelListViewModel
import androidx.compose.runtime.snapshotFlow

@Composable
fun TravelListScreen(
    viewModel: TravelListViewModel = hiltViewModel(),
    title: String,
    city: String? = null,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val destinations by viewModel.destinations.collectAsState()


    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItemCount = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index == totalItemCount - 2 // 마지막에서 2번째 친구면
        }.collect { isLastItemVisible ->
            if (isLastItemVisible) {
                if (city != null)
                    viewModel.getNextPage(city)
            }

        }
    }

    LaunchedEffect(Unit) {
        viewModel.initUserIdAndScrapList()
        if (city != null && viewModel.destinations.value.isEmpty()) {
            viewModel.getTravelList(city)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(start = 14.dp, end = 14.dp, top = 5.dp)
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
                            )

                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            Log.d("검색", "검색아이콘 눌림")
                                        }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_search),
                                        contentDescription = "검색",
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                                Spacer(modifier = Modifier.width(5.dp))

                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable {
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

                    CategorySelector(
                        categories = listOf("추천순", "인기순", "리뷰순"),
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.setCategory(it) }
                    )

                    Spacer(modifier = Modifier.height(19.dp))

                }
            }
        }
        destinations.forEach { destination ->
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
                ) {
                    val isFavorite = viewModel.scrapList.value.contains(destination.id)
                    DestinationCard(
                        destination = destination,
                        isFavorite = isFavorite,
                        onFavoriteToggle = {
                            viewModel.toggleScrap(destination.id)
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
