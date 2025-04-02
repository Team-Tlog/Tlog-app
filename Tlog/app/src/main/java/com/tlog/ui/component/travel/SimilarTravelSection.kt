package com.tlog.ui.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont


@Composable
fun SimilarTravelSection() {
    val tmpList = listOf("", "", "", "", "", "", "") // api 완성 시 수정 할 것 임시로 UI 나오는거 보려고 함
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = (31.5).dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "비슷한 여행지",
                fontFamily = MainFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(tmpList) {
                        SimilarTravelSpots()
                    }
                }
            }
        }
    }
}