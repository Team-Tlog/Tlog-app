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
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initUserId(context)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column {
            TopBar(
                text = "내 장바구니",
                fontSize = 15.sp,
                height = 52.dp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
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

            TravelList(
                travelList = viewModel.cartList.value,
            )
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