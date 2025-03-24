package com.tlog.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TeamCard
import com.tlog.ui.theme.MainFont
import com.tlog.R
import com.tlog.data.model.TeamData

@Preview(showBackground = true)
@Composable
fun MyTeamListScreen() {
    val teams = listOf(
        //나중에는 viewmodel에서 서버와 통신하며 여기로 데이터를 전송해줄 예정
        TeamData("동해번쩍", "제주도", "홍길동", listOf(R.drawable.test_image, R.drawable.test_image, R.drawable.test_image)),
        TeamData("독수리오형제", "부산", "이순신", listOf(R.drawable.test_image, R.drawable.test_image)),
        TeamData("컴공팀", "강원도", "김여행", listOf(R.drawable.test_image, R.drawable.test_image, R.drawable.test_image, R.drawable.test_image))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(53.dp))

        Text(
            text = "자신의 팀 보기",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = MainFont,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(teams) { team ->
                TeamCard(team = team)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        MainButton(
            text = "코드 입력해서 팀 합류",
            onClick = { /* 코드 입력하는 화면으로 이동 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MainButton(
            text = "팀 생성",
            onClick = { /* 팀 만드는 화면으로 이동 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}