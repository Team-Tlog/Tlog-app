package com.tlog.ui.component.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import com.tlog.data.api.TeamData

@Composable
fun TeamCard(team: TeamData) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(106.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                //Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = team.teamName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MainFont
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = //team.teamDestination, //아직 백에서 만들어지지 않음 나중에 dataclass에 추가 필요
                        "여행지",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = MainFont,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = team.teamLeaderName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = MainFont,
                )
            }

            /*
            Row(horizontalArrangement = Arrangement.spacedBy((-8).dp)) { // 아직 백에서 만들어지지 않음 나중에 dataclass에 추가 필요
                team.memberImage.forEach { imageResId ->
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "팀원 이미지",
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.LightGray, shape = CircleShape)
                            .clip(CircleShape)
                    )
                }
            }*/
        }

    }
}
