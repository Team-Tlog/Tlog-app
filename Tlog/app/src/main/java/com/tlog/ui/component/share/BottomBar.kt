package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tlog.R

@Composable
fun BottomBar(
    navController: NavController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val icons = listOf(
        Pair(R.drawable.ic_main_selected, R.drawable.ic_main),
        Pair(R.drawable.ic_course_selected, R.drawable.ic_course),
        Pair(R.drawable.ic_sns_selected, R.drawable.ic_sns),
        Pair(R.drawable.ic_mypage_selected, R.drawable.ic_mypage)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(68.dp) //아이콘 간격
        ) {
            // 왼쪽 여백, 피그마에선 왼쪽 오른쪽 30씩 줬지만 좌우 대칭이 맞지 않아 임의로 수정
            Spacer(modifier = Modifier.width(50.dp))

            icons.forEachIndexed { index, (selectedIcon, defaultIcon) ->
                Icon(
                    painter = painterResource(id = if (index == selectedIndex) selectedIcon else defaultIcon),
                    contentDescription = null,
                    tint = Color.Unspecified, // 아이콘 본래 색상 유지
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onTabSelected(index)
                            when (index) {
                                0 -> navController.navigate("main")
                                1 -> navController.navigate("course")
                                2 -> navController.navigate("sns")
                                3 -> navController.navigate("mypage")
                            }
                        }
                )
            }
        }
    }
}
