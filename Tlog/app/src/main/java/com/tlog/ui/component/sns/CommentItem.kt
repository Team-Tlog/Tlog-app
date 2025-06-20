package com.tlog.ui.component.sns

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.data.model.sns.Comment
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.theme.MainFont


@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // 사용자 닉네임
        Text(
            text = comment.authorName,
            fontFamily = MainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            modifier = Modifier.padding(end = 17.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 댓글 내용만 표시
        Text(
            text = comment.content,
            style = Body2Regular,
            fontSize = 14.sp
        )
    }
}