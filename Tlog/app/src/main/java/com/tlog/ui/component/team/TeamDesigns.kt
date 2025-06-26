package com.tlog.ui.component.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.team.DetailTeam
import com.tlog.data.model.team.Member
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.style.SubTitle
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun SmallDesign(
    teamData: DetailTeam,
    showPopup: Boolean,
    addMemberClick: () -> Unit,
    onDismiss: () -> Unit

) {
    Column(
        modifier = Modifier
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
                .padding(horizontal = 24.dp, vertical = 10.dp)
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
                        style = SubTitle,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TeamMemberImageGroup(
                        memberImageUrls = listOf(
                            "",
                            "",
                            "",
                            ""
                        ),
                        addMemberClick = addMemberClick

                    )
                }

                Text(
                    text = "${teamData.startDate} ~ ${teamData.endDate}",
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    color = Color.White,
                    modifier = Modifier
                        .padding(bottom = 38.dp)
                )
            }
        }
        if (showPopup)
            TeamDialog(teamCode = teamData.inviteCode, onDismiss = { onDismiss() })
    }
}



@Composable
fun DefaultDesign(
    teamData: DetailTeam,
    showPopup: Boolean,
    addMemberClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = MainColor,
                shape = RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
    ) {
        TeamTopBar()

        Spacer(modifier = Modifier.height((36.5).dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column {
                Text(
                    text = teamData.teamName,
                    style = SubTitle,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "TBTI", //teamData.teamTBTI,
                    style = BodyTitle,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                TeamMemberImageGroup(
                    memberImageUrls = listOf(
                        "",
                        "",
                        "",
                        ""
                    ),
                    addMemberClick = addMemberClick
                )

                Spacer(modifier = Modifier.height((7.5).dp))

                Text(
                    text = "${teamData.startDate} ~ ${teamData.endDate}",
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    color = Color.White,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
            }
        }

        if (showPopup)
            TeamDialog(teamCode = teamData.inviteCode, onDismiss = { onDismiss() })
    }
}

//@Composable
//fun MediumDesign(
//    teamData: DetailTeam,
//    showPopup: Boolean,
//    addMemberClick: () -> Unit,
//    onDismiss: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .background(
//                color = MainColor,
//                shape = RoundedCornerShape(
//                    bottomStart = 30.dp,
//                    bottomEnd = 30.dp
//                )
//            )
//    ) {
//        TeamTopBar()
//
//        Spacer(modifier = Modifier.height(9.dp))
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 24.dp)
//        ) {
//            Column {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = teamData.teamName,
//                        style = SubTitle,
//                        color = Color.White
//                    )
//
//                    Spacer(modifier = Modifier.width(10.dp))
//
//                    Text(
//                        text = "TBTI", //teamData.teamTBTI,
//                        style = BodyTitle,
//                        color = Color.White
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(30.dp))
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(32.dp)
//                ) {
//                    TeamMemberImageGroup(
//                        memberImageUrls = listOf(
//                            "",
//                            "",
//                            "",
//                            ""
//                        ),
//                        addMemberClick = addMemberClick
//                    )
//
//                    Spacer(modifier = Modifier.weight(1f))
//
//                    AddMemberBox(
//                        onClick = {
//                            addMemberClick()
//                        }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                TravelDateBox(teamData.startDate, teamData.endDate)
//            }
//        }
//    }
//    if (showPopup)
//        TeamDialog(teamCode = teamData.inviteCode, onDismiss = { onDismiss() })
//}

@Composable
fun BigDesign(
    teamData: DetailTeam,
) {
    Column(
        modifier = Modifier
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
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    text = teamData.teamName,
                    style = SubTitle,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "TBTI", //teamData.teamTBTI,
                    style = BodyTitle,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                DetailMember(
                    listOf(
                        Member(
                            memberId = "tmp",
                            memberName = "고중수",
                            isLeader = true
                        ),
                        Member(
                            memberId = "tmp",
                            memberName = "박신욱",
                            isLeader = true
                        ),
                        Member(
                            memberId = "tmp",
                            memberName = "백성수",
                            isLeader = true
                        ),
                        Member(
                            memberId = "tmp",
                            memberName = "서준우",
                            isLeader = true
                        ),
                        Member(
                            memberId = "tmp",
                            memberName = "정찬",
                            isLeader = true
                        )
                    )
                )
            }
        }

        Text(
            text = "${teamData.startDate} ~ ${teamData.endDate}",
            style = TextStyle(
                fontFamily = MainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
            color = Color.White,
            modifier = Modifier
                .padding(start = 24.dp, bottom = 16.dp)
        )
    }
}

@Composable
fun DetailMember(
    memberList: List<Member>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        memberList.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.login_ic_naver), // 추후 뷰모델 연결하기
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = it.memberName,
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
    }
}
