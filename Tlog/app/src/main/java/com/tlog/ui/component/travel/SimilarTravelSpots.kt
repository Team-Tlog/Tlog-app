package com.tlog.ui.component.travel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont


@Composable
fun SimilarTravelSpots() {
    Box(
        modifier = Modifier
            .width(150.dp)
    ) {
        Column {
            Box (
                modifier = Modifier
                    .width(150.dp)
                    .height(158.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.tmp_jeju),
                    contentDescription = "여행지 사진", // 추후 변경
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(14))
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "여행지 이름",
                fontFamily = MainFont,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용",
                fontFamily = MainFont,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .height(18.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            BlueHashTagGroup(
                hashTags = listOf("해시태그", "해시태그", "해시태그", "해시태그", "해시태그", "해시태그"),
                maxCnt = 2
            )
        }
    }
}