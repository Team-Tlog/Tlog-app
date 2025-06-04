package com.tlog.ui.screen.share

import android.util.Log
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.component.share.TwoMainButtons
import com.tlog.ui.component.travel.ScrapTravelList
import com.tlog.ui.component.travel.TravelList
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.share.CartViewModel
import com.tlog.R
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.style.Body1Regular

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.initUserIdAndCart()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        val selectedTab = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("내 장바구니") }

        Column {

            // 상단 탭 Row
            if (viewModel.checkedTravelList.value.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 10.dp)
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
                        text = selectedTab.value,
                        style = Body1Bold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_trashbin),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                viewModel.deleteSelectedItems(selectedTab.value)
                            }
                    )
                }
            } else {
                // 스크랩/내 장바구니 선택 Row (아이템이 선택되지 않은 경우만 표시)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "스크랩",
                        fontSize = 15.sp,
                        color = if (selectedTab.value == "스크랩") Color.Black else Color.Gray,
                        modifier = Modifier
                            .clickable {
                                selectedTab.value = "스크랩"
                                viewModel.fetchScrapList(viewModel.userId)
                            }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "내 장바구니",
                        fontSize = 15.sp,
                        color = if (selectedTab.value == "내 장바구니") Color.Black else Color.Gray,
                        modifier = Modifier
                            .clickable {
                                selectedTab.value = "내 장바구니"
                                viewModel.fetchCart(viewModel.userId)
                            }
                    )
                }
            }

            // Row for 전체 선택
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
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        viewModel.allChecked(selectedTab.value)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(10.dp))

            if (selectedTab.value == "스크랩") {
                ScrapTravelList(
                    scrapTravelList = viewModel.scrapList.value
                )
            } else {
                TravelList(
                    travelList = viewModel.cartList.value
                )
            }
        }


        // 버튼 조건 분기
        AnimatedVisibility(
            visible = viewModel.checkedTravelList.value.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        ) {
            if (selectedTab.value == "스크랩") {
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

                    }
                )
            }
        }
    }
}