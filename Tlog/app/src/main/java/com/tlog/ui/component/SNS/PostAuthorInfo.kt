package com.tlog.ui.component.SNS

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun PostAuthorInfo(
    userId: String,
    userProfileImageUrl: String,
    isFollowing: Boolean,
    clickUser: () -> Unit = {},
    onFollowToggle: () -> Unit,
    isMyPost: Boolean = false,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(Modifier.padding(contentPadding)), // 외부에서 패딩 설정 가능
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 프로필 사진
        AsyncImage(
            model = userProfileImageUrl,
            contentDescription = null,
            error = painterResource(id = R.drawable.tmp_jeju),
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable { clickUser() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 사용자 이름
        Text(
            text = userId,
            style = Body1Bold,
            modifier = Modifier
                .clickable { clickUser() }
        )

        Spacer(modifier = Modifier.weight(1f))

        // 팔로우 버튼
        if (isMyPost == false) {
            Button(
                onClick = { onFollowToggle() },
                modifier = Modifier
                    .width(72.dp)
                    .height(32.dp),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor)
            ) {
                Text(
                    text = if (isFollowing) "팔로잉" else "팔로우",
                    fontFamily = MainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}