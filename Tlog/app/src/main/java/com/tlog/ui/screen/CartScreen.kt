package com.tlog.ui.screen

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TopBar
import com.tlog.ui.component.TravelList
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.CartViewModel


@Preview
@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 118.dp)
        ) {
            TopBar(
                text = "내 장바구니",
                fontSize = 15.sp,
                height = 48.dp
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
                    fontFamily = MainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = MainColor
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TravelList(
                travelList = viewModel.travelList.value,
                setCheckBox = { index, checked ->
                    viewModel.updateChecked(index, checked)
                }
            )
        }

        AnimatedVisibility(
            visible = viewModel.getCheckedTravelList().isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 63.dp) // 48 + 15
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