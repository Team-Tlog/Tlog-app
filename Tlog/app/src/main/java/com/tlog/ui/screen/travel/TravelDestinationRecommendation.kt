package com.tlog.ui.screen.travel

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.travel.TravelDestinationRecommendationViewModel


@Preview
@Composable
fun TravelDestinationRecommendation(viewModel: TravelDestinationRecommendationViewModel = viewModel()) {

    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val destinations by viewModel.destinations.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()           // 키보드가 딸려 올라오도록
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

                Text(
                    text = "000의 여행지 추천",
                    fontSize = 24.sp,
                    fontFamily = MainFont,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                val categories = listOf("추천순", "인기순", "리뷰순")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    categories.forEachIndexed { index, category ->
                        if (index > 0) {
                            Box(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(1.dp)
                                    .background(Color.LightGray)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clickable { viewModel.setCategory(category) }
                        ) {
                            Text(
                                fontFamily = MainFont,
                                text = category,
                                color = if (category == selectedCategory) Color.Blue else Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    destinations.forEach { destination ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { println("Clicked: ${destination.name}") },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                                    contentDescription = destination.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.2f))
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = destination.name,
                                                color = Color.White,
                                                fontSize = 22.sp,
                                                fontFamily = MainFont,
                                                fontWeight = FontWeight.Bold
                                            )

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_map_pin),
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                )

                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = destination.location,
                                                    color = Color.White,
                                                    fontFamily = MainFont,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                        Icon(
                                            imageVector = if (destination.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                            contentDescription = "Favorite",
                                            tint = if (destination.isFavorite) Color.Red else Color.White,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clickable { viewModel.toggleFavorite(destination.id) }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(69.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        HashTagsGroup(hashTags = destination.tags)

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_filled_star),
                                                contentDescription = null,
                                                tint = Color.Yellow,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                fontFamily = MainFont,
                                                text = "${destination.rating}(${destination.reviewCount})",
                                                color = Color.White,
                                                fontWeight = FontWeight.Light,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

