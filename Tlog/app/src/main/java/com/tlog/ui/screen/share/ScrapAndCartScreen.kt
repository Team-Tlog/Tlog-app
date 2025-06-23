package com.tlog.ui.screen.share

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.TwoMainButtons
import com.tlog.ui.component.travel.ScrapTravelList
import com.tlog.ui.component.travel.TravelList
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.share.ScrapAndCartViewModel
import com.tlog.R
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainFont


@Composable
fun ScrapAndCartScreen(
    viewModel: ScrapAndCartViewModel = hiltViewModel(),
    navController: NavHostController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column {
            if (viewModel.checkedTravelList.value.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = (13.5).dp)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "취소",
                            style = Body1Regular,
                            color = Color.Black,
                            modifier = Modifier.clickable {
                                viewModel.clearChecked()
                            }
                        )
                    }
                    Text(
                        text = viewModel.selectedTab.value,
                        style = TextStyle(
                            fontFamily = MainFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_trashbin),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(15.dp)
                            .clickable {
                                viewModel.deleteSelectedItems(viewModel.selectedTab.value)
                            }
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .drawBehind {
                                val strokeWidth = 2.dp.toPx()
                                val y = size.height - strokeWidth / 2
                                drawLine(
                                    color = if (viewModel.selectedTab.value == "스크랩") MainColor else Color.Transparent,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = strokeWidth
                                )
                            }
                            .clickable {
                                viewModel.updateSelectedTab("스크랩")
                                viewModel.fetchScrapList()
                            }
                            .weight(1f)
                    ) {
                        Text(
                            text = "스크랩",
                            fontFamily = MainFont,
                            fontSize = 18.sp,
                            fontWeight = if (viewModel.selectedTab.value == "스크랩") FontWeight.Bold else FontWeight.Medium,
                            color = if (viewModel.selectedTab.value == "스크랩") MainColor else Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = (11.5).dp)
                                .fillMaxWidth()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .drawBehind {
                                val strokeWidth = 2.dp.toPx()
                                val y = size.height - strokeWidth / 2
                                drawLine(
                                    color = if (viewModel.selectedTab.value == "내 장바구니") MainColor else Color.Transparent,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = strokeWidth
                                )
                            }
                            .clickable {
                                viewModel.updateSelectedTab("내 장바구니")
                                viewModel.fetchCart()
                            }
                            .weight(1f)
                    ) {
                        Text(
                            text = "내 장바구니",
                            fontSize = 18.sp,
                            fontFamily = MainFont,
                            fontWeight = if (viewModel.selectedTab.value == "내 장바구니") FontWeight.Bold else FontWeight.Medium,
                            color = if (viewModel.selectedTab.value == "내 장바구니") MainColor else Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = (11.5).dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "전체 선택",
                    color = MainColor,
                    fontFamily = MainFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        viewModel.allChecked(viewModel.selectedTab.value)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(10.dp))

            if (viewModel.selectedTab.value == "스크랩") {
                ScrapTravelList(
                    scrapTravelList = viewModel.scrapList.value,
                    onClick = { travelId ->
                        navController.navigate("travelInfo/$travelId")
                    }
                )
            } else {
                TravelList(
                    travelList = viewModel.cartList.value,
                    onClick = { travelId ->
                        navController.navigate("travelInfo/$travelId")
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = viewModel.checkedTravelList.value.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        ) {
            if (viewModel.selectedTab.value == "스크랩") {
                TwoMainButtons(
                    onLeftClick = {
                        viewModel.addSelectedTravelToCart()
                    },
                    onRightClick = {
                        // AI 코스 짜기 버튼
                    }
                )
            } else {
                MainButton(
                    text = "코스 짜기",
                    onClick = {

                    },
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 15.dp)
                )
            }
        }
    }
}