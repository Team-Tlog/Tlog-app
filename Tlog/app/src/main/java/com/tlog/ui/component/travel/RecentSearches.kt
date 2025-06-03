package com.tlog.ui.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import androidx.compose.ui.draw.shadow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecentSearches(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "최근검색어",
            fontFamily = MainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            repeat(5) { // 하드코딩된 더미 데이터 개수
                Box(
                    modifier = Modifier
                        .size(width = 43.dp, height = 22.dp)
                        .padding(1.dp)
                        .shadow(2.dp, RoundedCornerShape(50))
                        .background(Color.White)
                ) {
                    Text(
                        text = "검색어",
                        fontFamily = MainFont,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF3C3C3C),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}