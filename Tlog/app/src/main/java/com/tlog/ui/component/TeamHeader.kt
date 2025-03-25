package com.tlog.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.TeamData
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun TeamHeader(
    team: TeamData,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    height: Dp,
    scrollOffset: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(MainColor)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .padding(20.dp)
    ) {
        // 로고와 아이콘 (항상 고정)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = team.members.first().imageResId),
                contentDescription = "로고",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "검색", modifier = Modifier.size(24.dp))
                Image(painter = painterResource(id = R.drawable.ic_alert), contentDescription = "알림", modifier = Modifier.size(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 팀 이름 (항상 고정)
        Text(
            text = team.teamName,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = MainFont
        )

        // 스크롤 정도에 따라 애니메이션 위치 변화
        if (scrollOffset < 100 || expanded) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = team.teamTBTI ?: "",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = MainFont
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy((-8).dp),
                modifier = Modifier.clickable { onToggleExpand() }
            ) {
                team.members.forEach {
                    Image(
                        painter = painterResource(id = it.imageResId),
                        contentDescription = "팀원",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    modifier = Modifier
                        .background(Color(0x883B3BFB), shape = CircleShape)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "팀원초대", fontSize = 12.sp, color = Color.White, fontFamily = MainFont)
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "초대",
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .background(Color(0x553B3BFB), shape = CircleShape)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = team.teamStartDate ?: "", fontSize = 12.sp, color = Color.White, fontFamily = MainFont)
                Text(text = "~", fontSize = 12.sp, color = Color.White, fontFamily = MainFont)
                Text(text = team.teamEndDate ?: "", fontSize = 12.sp, color = Color.White, fontFamily = MainFont)
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    team.members.forEach {
                        Text(
                            text = "${it.name} - ${it.tbti}",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontFamily = MainFont
                        )
                    }
                }
            }
        }
    }
}
