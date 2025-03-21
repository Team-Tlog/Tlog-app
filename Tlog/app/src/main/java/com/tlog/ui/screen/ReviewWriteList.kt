package com.tlog.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.component.TopFieldBar



object Variables {
    val backgroundBlue: Color = Color(0xFFF1F4FD)
}
@Preview
@Composable
fun ReviewWriteList() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val topPadding = maxHeight * 0.20f
            val startPadding = maxWidth * 0.11f
            val midPadding = maxHeight * 0.35f
            val iconTopPadding = maxHeight * 0.04f
            val bottomPadding = maxHeight * 0.1f
            val iconSpace = maxWidth * 0.1f
            val iconSize = maxWidth * 0.14f

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                // ✅ TopField 컴포넌트 적용
                TopFieldBar(
                    text = "리뷰작성",
                    onBackClick = {
                        // TODO: 뒤로 가기 동작 추가 (예: 네비게이션 뒤로 가기)
                        println("뒤로 가기 버튼 클릭됨")
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                // ✅ 검색창
                Box(
                    modifier = Modifier
                        .width(327.dp)
                        .height(52.dp)
                        .background(
                            color = Variables.backgroundBlue,
                            shape = RoundedCornerShape(size = 15.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp) // Apply equal padding on left and right
                        .align(Alignment.CenterHorizontally),  // Center horizontally
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search), // 돋보기 아이콘
                            contentDescription = "검색",
                            tint = Color.Gray
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "검색어를 입력하세요",
                            fontSize = 15.sp,
//                            fontFamily = MainFont,   /*폰트 추가 해야함*/
                            fontWeight = FontWeight(400),
                            color = Color(0xFF676767),
                        )
                    }
                }
            }
        }
    }
}