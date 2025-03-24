package com.tlog.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont

@Composable
fun TeamCard() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "팀이름",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MainFont
                )
                Text(
                    text = "여행지",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MainFont,
                    color = Color.Gray
                )
                Text(
                    text = "팀장",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MainFont,
                    color = Color.Gray
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy((-8).dp)) {
                repeat(4) {
                    Image(
                        painter = painterResource(id = R.drawable.test_image),
                        contentDescription = "팀원 이미지",
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.LightGray, shape = CircleShape)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}
