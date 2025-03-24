package com.tlog.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TeamCard
import com.tlog.ui.theme.MainFont

@Preview(showBackground = true)
@Composable
fun MyTeamListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = "자신의 팀 보기",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = MainFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        repeat(3) {
            TeamCard()
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            text = "코드 입력해서 팀 합류",
            onClick = { /* 코드 복사, 공유 창 띄움 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MainButton(
            text = "팀 생성",
            onClick = { /* 팀 생성 화면으로 넘기기 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}
