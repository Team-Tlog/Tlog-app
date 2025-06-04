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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.component.travel.TravelList
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.share.CartViewModel


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
        Column {
            // State to control selected tab
            val selectedTab = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("내 장바구니") }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "스크랩",
                        fontSize = 15.sp,
                        color = if (selectedTab.value == "스크랩") Color.Black else Color.Gray,
                        modifier = Modifier.clickable {
                            selectedTab.value = "스크랩"
                            viewModel.fetchScrapList(viewModel.userId)
                        }
                    )
                    Text(
                        text = "내 장바구니",
                        fontSize = 15.sp,
                        color = if (selectedTab.value == "내 장바구니") Color.Black else Color.Gray,
                        modifier = Modifier.clickable {
                            selectedTab.value = "내 장바구니"
                            viewModel.fetchCart(viewModel.userId)
                        }
                    )
                }
                Text(
                    text = "전체 선택",
                    modifier = Modifier.clickable {
                        viewModel.allChecked()
                        Log.d("all select", "my click!!")
                    },
                    style = Body2Regular,
                    color = MainColor
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (selectedTab.value == "스크랩") {
                TravelList(
                    travelList = viewModel.scrapList.value
                )
            } else {
                TravelList(
                    travelList = viewModel.cartList.value
                )
            }
        }

        AnimatedVisibility( // 애니메이션
            visible = viewModel.checkedTravelList.value.isNotEmpty(), // 체크된 List가 비어있지 않으면 띄우기
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 15.dp)
        ) {
            MainButton(
                text = "AI 코스 짜기",
                onClick = {
                    Log.d("AI", "my click!!")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }

    }
}