package com.tlog.ui.component.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

import androidx.compose.ui.window.Dialog

@Composable
fun ReviewNoticeDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .width(333.dp)
                .height(346.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "리뷰 주의사항",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = MainFont,
                    color = MainColor
                )

                Spacer(modifier = Modifier.height(17.dp))

                NoticeItem(
                    title = "1. 직접 사용한 경험만 적어주세요",
                    body = "사용해본 상황을 간단히 적어주세요.\n(언제, 어디서, 어떤 기기에서)"
                )
                NoticeItem(
                    title = "2. 구체적으로 적어주세요",
                    body = "좋았던 점 / 아쉬웠던 점을 짧고 명확하게 써주세요."
                )
                NoticeItem(
                    title = "3. 누구에게 유용한지 알려주세요",
                    body = "혼자여행, 가족여행 등 추천 대상이 있다면 알려주세요."
                )
                NoticeItem(
                    title = "4. 개인정보 주의",
                    body = "예약번호, 연락처 등은 사진이나 글에서 꼭 가려주세요."
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "확인",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onDismiss() }
                        .padding(vertical = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
