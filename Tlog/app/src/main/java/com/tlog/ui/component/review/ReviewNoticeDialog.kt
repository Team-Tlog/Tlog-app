package com.tlog.ui.component.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import androidx.compose.ui.window.Dialog
import com.tlog.R


@Preview
@Composable
fun ReviewNoticeDialog(
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 35.dp, bottom = 50.dp, start = 24.dp, end = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_infomation_blue),
                        contentDescription = "도움말",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = "리뷰 작성시 주의사항",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MainFont,
                        color = MainColor
                    )
                }

                Spacer(modifier = Modifier.height(29.dp))

                NoticeItem(
                    title = "직접 사용한 경험만 적어주세요",
                    body = "언제/어디서/어떻게 사용 상황을 작성해주세요."
                )

                Spacer(modifier = Modifier.height(14.dp))

                NoticeItem(
                    title = "구체적으로 작성해 주세요",
                    body = "좋았던 점/아쉬웠던 점을 명확하게 써주세요."
                )

                Spacer(modifier = Modifier.height(14.dp))

                NoticeItem(
                    title = "누구에게 유용한지 알려주세요",
                    body = "혼자여행, 가족여행 등 추천 대상이 있다면 알려주세요."
                )

                Spacer(modifier = Modifier.height(14.dp))

                NoticeItem(
                    title = "개인정보 주의",
                    body = "예약번호, 연락처 등은 사진이나 글에서 꼭 가려주세요."
                )
            }
        }
    }
}
