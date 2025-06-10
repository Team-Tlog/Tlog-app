package com.tlog.ui.component.SNS

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import com.tlog.R
import com.tlog.ui.style.Body2Regular

@Composable
fun PostContentAndInteractions(
    content: String,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit,
    onReportClick: () -> Unit,
    isSingleLine: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    interactionsPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // 게시물 내용 표시 (내용이 있을 경우에만)
        if (content.isNotEmpty()) {
            var isTextTruncated by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                Text(
                    text = content,
                    style = Body2Regular,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (isSingleLine == true) 1 else Int.MAX_VALUE, // 맘에 안들긴해.. 일단 패스
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (isTextTruncated) 0.dp else 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 좋아요, 공유, 신고 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(interactionsPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 좋아요 버튼
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { onLikeClick() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "좋아요",
                    modifier = Modifier.size(30.dp),
                    tint = if (isLiked) Color.Red else Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // 공유 버튼
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable { onShareClick() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "공유하기",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // 신고하기 버튼
            Text(
                text = "신고하기",
                style = Body2Regular,
                modifier = Modifier.clickable { onReportClick() }
            )
        }
    }
}