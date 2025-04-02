package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont

@Composable
fun HashTagsGroup(
    hashTags: List<String>,
    space: Dp = 8.dp
) {
    LazyRow(
        //contentPadding = PaddingValues(horizontal = 24.dp), // 여행지 등록, 리뷰 쓰기에 사용 가능 추후 변경할 것
        horizontalArrangement = Arrangement.spacedBy(space)
    ) {
        items(hashTags) { tag ->
            //val textWidth = 4 * tag.length + 25 // 박스 크기

            Surface(
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp) // 상하좌우 그림자 짤리기 방지
                    .shadow(2.dp, RoundedCornerShape(50))
                    .background(Color.White)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .height(22.dp)
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                        //.width(textWidth.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "#$tag",
                        fontSize = 8.sp,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}