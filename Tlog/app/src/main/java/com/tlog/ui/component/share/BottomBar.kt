package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tlog.R

@Preview
@Composable
fun BottomBar(
    navController: NavController = rememberNavController(),
    selectedIndex: Int = 0,
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
                .padding(top = 18.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            icons.forEachIndexed { index, (selectedIcon, defaultIcon) ->
                Icon(
                    painter = painterResource(id = if (index == selectedIndex) selectedIcon else defaultIcon),
                    contentDescription = null,
                    tint = Color.Unspecified, // 아이콘 본래 색상 유지
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
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
