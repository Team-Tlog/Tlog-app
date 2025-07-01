package com.tlog.ui.component.notification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.tlog.data.model.notification.TsnsNotificationData
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.TsnsNotificationViewModel
import com.tlog.R

@Composable
fun TsnsNotificationList(viewModel: TsnsNotificationViewModel = viewModel()) {
    val tsnsNotifications by viewModel.tsnsNotifications.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        items(tsnsNotifications) { item ->
            TsnsNotificationItem(item = item) // 이렇게 넘기자
        }
    }
}

@Composable
fun TsnsNotificationItem(item: TsnsNotificationData) { // 깔끔하게 묶기
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.userProfileImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.login_ic_google)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // userName + action 이어서 출력 (userName만 Bold)
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(item.userName)
                    }
                    append(item.action)
                },
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.time,
                fontFamily = MainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF727272)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (item.showFollowButton) {
            Button(
                onClick = { /* 맞팔로우 기능 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4F8FF9),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(32.dp)
                    .width(73.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "맞팔로우",
                    fontSize = 12.sp,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        } else {
            AsyncImage(
                model = item.postThumbnailImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(63.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.login_ic_google)
            )
        }
    }
}
