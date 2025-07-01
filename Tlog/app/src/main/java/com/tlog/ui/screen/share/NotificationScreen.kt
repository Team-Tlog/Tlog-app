package com.tlog.ui.screen.share

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.ui.component.notification.AppNotificationList
import com.tlog.ui.component.notification.TsnsNotificationList
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.NotificationViewModel

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    navController: NavController,
    previousSelectedIndex: Int
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopBar(text = "알림")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 13.dp)
        ) {
            Box(
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = if (viewModel.selectedTab.value == "새 소식") MainColor else Color(0xFF969696),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .clickable {
                        if (viewModel.selectedTab.value != "새 소식") {
                            viewModel.updateSelectedTab("새 소식")
                            // 새소식 가져오기
                        }
                    }
                    .weight(1f)
            ) {
                Text(
                    text = "새 소식",
                    fontFamily = MainFont,
                    fontSize = 18.sp,
                    fontWeight = if (viewModel.selectedTab.value == "새 소식") FontWeight.Bold else FontWeight.Medium,
                    color = if (viewModel.selectedTab.value == "새 소식") MainColor else Color.Gray,
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
                            color = if (viewModel.selectedTab.value == "T-SNS") MainColor else Color(0xFF969696),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .clickable {
                        if (viewModel.selectedTab.value != "T-SNS") {
                            viewModel.updateSelectedTab("T-SNS")
                            // SNS 불러오기 (로컬에 두지않을까 싶음)
                        }
                    }
                    .weight(1f)
            ) {
                Text(
                    text = "T-SNS",
                    fontFamily = MainFont,
                    fontSize = 18.sp,
                    fontWeight = if (viewModel.selectedTab.value == "T-SNS") FontWeight.Bold else FontWeight.Medium,
                    color = if (viewModel.selectedTab.value == "T-SNS") MainColor else Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = (11.5).dp)
                        .fillMaxWidth()
                )
            }
        }

        if (viewModel.selectedTab.value == "새 소식") {
            AppNotificationList(viewModel)
        }
        else {
            TsnsNotificationList()
        }



        Spacer(modifier = Modifier.weight(1f))

        BottomBar(navController = navController, selectedIndex = previousSelectedIndex)
    }
}
