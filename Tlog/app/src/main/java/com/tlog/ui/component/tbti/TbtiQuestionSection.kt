package com.tlog.ui.component.tbti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun TbtiQuestionSection( // 추후 퀘스쳔 번호만 받아와서 우리 오브젝트에서 불러와서 할 수 있도록 하는게 베스트일 듯!
    question: String = "여행을 가기 전날 밤\n내 가방의 상태는?", // 질문
    chooseText1: String = "이미 갈 준비 완료! 편하게 자볼까?", // 선택지 1
    chooseText2: String = "벌써 내일이야? 빨리 짐싸자!!" // 선택지 2,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Q.",
            color = MainColor,
            fontSize = 25.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = question,
            fontSize = 23.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = (84.5).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            TbtiTestAnswerBox(1, chooseText1)

            TbtiTestAnswerBox(2, chooseText2)
        }
    }
}