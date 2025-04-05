package com.tlog.ui.component.team

import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.team.TeamData
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.style.SubTitle
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
                        style = SubTitle
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = teamData.teamTBTI,
                        style = BodyTitle
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

                TravelDateBox(teamData.teamStartDate, teamData.teamEndDate)
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
                    style = SubTitle
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = teamData.teamTBTI,
                    style = BodyTitle
                )

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "${teamData.teamStartDate} ~ ${teamData.teamEndDate}",
                    style = Body1Regular,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(15.dp))

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

                    Spacer(modifier = Modifier.weight(1f))

                    AddMemberBox(
                        {
                            Log.d("add member", "my click!!")
                        }
                    )
                }

                //TravelDateBox(teamData.teamStartDate, teamData.teamEndDate)
            }
        }
    }
}

//236
@Composable
fun MidiumDesign(
    teamData: TeamData
) {
    Column(
        modifier = Modifier
            .height(236.dp)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = teamData.teamName,
                        style = SubTitle
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = teamData.teamTBTI,
                        style = BodyTitle
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

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

                    Spacer(modifier = Modifier.weight(1f))

                    AddMemberBox(
                        {
                            Log.d("add member", "my click!!")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TravelDateBox(teamData.teamStartDate, teamData.teamEndDate)
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
            .padding(bottom = 20.dp)
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
                    style = SubTitle
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = teamData.teamTBTI,
                    style = BodyTitle
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Column {
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

                    Spacer(modifier = Modifier.weight(1f))

                    AddMemberBox(
                        {
                            Log.d("add member", "my click!!")
                        }
                    )
                }

                //Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier.weight(1f)) // 맨아래 고정시킬지 ? 팀원 목록 아래 띄울지 고민할 것

                TravelDateBox(teamData.teamStartDate, teamData.teamEndDate)
            }
        }
    }
}