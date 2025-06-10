package com.tlog.ui.component.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.api.UserInfo
import com.tlog.ui.theme.MainFont


@Composable
fun MyPageTbtiGroup(
    userInfo: UserInfo
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if ( userInfo.tbtiDescription.imageUrl == null || userInfo.tbtiDescription.imageUrl == "") {
            Image(
                painter = painterResource(R.drawable.rena),
                contentDescription = "tbti 캐릭터",
                Modifier
                    .size(178.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFD9D9D9))
            )
        }
        else {
            AsyncImage(
                model = userInfo.tbtiDescription.imageUrl,
                contentDescription = "tbti 캐릭터",
                modifier = Modifier
                    .size(178.dp)
                    .clip(RoundedCornerShape(50))
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = userInfo.tbtiDescription.tbtiString,
            fontFamily = MainFont,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = userInfo.tbtiDescription.secondName,
            fontFamily = MainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "검사 다시하기",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.White
                )
                Icon(
                    painter = painterResource(R.drawable.ic_right_arrow),
                    contentDescription = "검사 다시하기",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}