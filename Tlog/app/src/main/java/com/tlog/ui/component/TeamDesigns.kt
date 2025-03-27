package com.tlog.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.TeamData
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun teamTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filled_star),
            contentDescription = "팀 대표 이미지",
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "검색",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(40.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "알림",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(42.dp)
        )

    }
}

@Composable
fun MemberImageGroup(
    memberImageUrls: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-9).dp) // 살짝 겹쳐 보이게 (6이 맞는거같은데 추후 물어보고 수정)
    ) {
        memberImageUrls.take(5).forEach { url ->
            Box(//AsyncImage(
                //model = url,
                //contentDescription = "팀원 이미지",
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}

@Composable
fun DesignOne(
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
        teamTopBar()

        Spacer(modifier = Modifier.height(9.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart),
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

                MemberImageGroup(
                    memberImageUrls = listOf(
                        "",
                        "",
                        "",
                        ""
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .height(34.dp)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomStart = 50.dp, bottomEnd = 50.dp))
                .background(Color(0x42000000)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${teamData.teamStartDate} ~ ${teamData.teamEndDate}",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = MainFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }
}



@Composable
fun DesignTwo(
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
        teamTopBar()

        Spacer(modifier = Modifier.height(9.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column (
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
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
                    MemberImageGroup(
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

                Box(
                    modifier = Modifier
                        .height(34.dp)
                        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomStart = 50.dp, bottomEnd = 50.dp))
                        .background(Color(0x42000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${teamData.teamStartDate}  ~  ${teamData.teamEndDate}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = MainFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DesignThree(
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
        teamTopBar()

        Spacer(modifier = Modifier.height(9.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column (
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
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

                Box(
                    modifier = Modifier
                        .height(34.dp)
                        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomStart = 50.dp, bottomEnd = 50.dp))
                        .background(Color(0x42000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${teamData.teamStartDate} ~ ${teamData.teamEndDate}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = MainFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}