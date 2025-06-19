package com.tlog.ui.component.team

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import com.tlog.R
import com.tlog.data.model.team.Team

@Composable
fun TeamCard(
    team: Team,
    onDeleteClick: (String) -> Unit,
    onClick: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(team.teamId) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 20.dp, start = 24.dp, end = 20.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = team.teamName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MainFont
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "여행지",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = MainFont,
                        color = Color.Gray
                    )
                    IconButton(onClick = { onDeleteClick(team.teamId) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Delete team",
                            tint = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = team.teamLeaderName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = MainFont,
                )
            }
        }
    }
}
