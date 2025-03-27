package com.tlog.ui.component.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
//import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.TeamData
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun SmallDesign(
    teamData: TeamData
) {
    Column(
        modifier = Modifier
            .height(183.dp)
            .background(
                color = MainColor,
                shape = RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
    ) {
        TeamTopBar()

        Spacer(modifier = Modifier.height(9.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column (
                modifier = Modifier
                    .align(Alignment.CenterStart),
            ){
                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = teamData.teamName,
                        color = Color.White,
                        fontFamily = MainFont,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = teamData.teamTBTI ?: "없음", // 없을 일이 없음 만들면 테스트 하기 때문 추후 수정할 것
                        color = Color.White,
                        fontFamily = MainFont,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TeamMemberImageGroup(
                        memberImageUrls = listOf(
                            "",
                            "",
                            "",
                            ""
                        )
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                TravelDateBox(teamData.teamStartDate ?: "", teamData.teamEndDate ?: "")
            }
        }
    }
}



@Composable
fun DefaultDesign(
    teamData: TeamData
) {
    Column(
        modifier = Modifier
            .height(288.dp)
            .background(
                color = MainColor,
                shape = RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
    ) {
        TeamTopBar()

        Spacer(modifier = Modifier.height(9.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column {
                Text(
                    text = teamData.teamName,
                    color = Color.White,
                    fontFamily = MainFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = teamData.teamTBTI ?: "없음", // 없을 일이 없음 만들면 테스트 하기 때문 추후 수정할 것
                    color = Color.White,
                    fontFamily = MainFont,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    TeamMemberImageGroup(
                        memberImageUrls = listOf(
                            "",
                            "",
                            "",
                            ""
                        )
                    )

                    // 팀원 초대
                }

                Spacer(modifier = Modifier.height(10.dp))

                TravelDateBox(teamData.teamStartDate ?: "", teamData.teamEndDate ?: "")
            }
        }
    }
}

@Composable
fun BigDesign(
    teamData: TeamData
) {
    Column(
        modifier = Modifier
            .height(368.dp)
            .background(
                color = MainColor,
                shape = RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
    ) {
        TeamTopBar()

        Spacer(modifier = Modifier.height(9.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column {
                Text(
                    text = teamData.teamName,
                    color = Color.White,
                    fontFamily = MainFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = teamData.teamTBTI ?: "없음", // 없을 일이 없음 만들면 테스트 하기 때문 추후 수정할 것
                    color = Color.White,
                    fontFamily = MainFont,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_filled_star), // 추후 뷰모델 연결하기
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )

                            Spacer(modifier = Modifier.width(15.dp))

                            Text(
                                text = "이름",
                                fontFamily = MainFont,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(9.dp))

                            Text(
                                text = "TBTI",
                                fontFamily = MainFont,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        }
                    }
                    // 팀원 초대
                }

                Spacer(modifier = Modifier.height(10.dp))

                TravelDateBox(teamData.teamStartDate ?: "", teamData.teamEndDate ?: "")
            }
        }
    }
}