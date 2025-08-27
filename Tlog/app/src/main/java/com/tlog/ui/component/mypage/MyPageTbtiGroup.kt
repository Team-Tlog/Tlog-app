package com.tlog.ui.component.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.share.Tbti
import com.tlog.data.model.user.User
import com.tlog.ui.theme.MainFont


@Composable
fun MyPageTbtiGroup(
    userInfo: User,
    tbtiTestClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                try {
                    Tbti.valueOf(userInfo.tbtiDescription.tbtiString).icon
                } catch (e: IllegalArgumentException) {
                    R.drawable.character_error
                }
            ),
            contentDescription = "tbti 캐릭터",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userInfo.username,
                fontFamily = MainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )

            VerticalDivider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier
                    .height(14.dp)
                    .padding(start = 26.dp, end = 26.dp)
            )

            Text(
                text = userInfo.tbtiDescription.tbtiString,
                fontFamily = MainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = userInfo.tbtiDescription.secondName,
            fontFamily = MainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height((34.5).dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 9.dp)
                .clickable { tbtiTestClick() }
        ) {
            Text(
                text = "검사 다시하기",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.White
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = "검사 다시하기",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}