package com.tlog.ui.component.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TeamMemberImageGroup(
    memberImageUrls: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-9).dp) // 살짝 겹쳐 보이게 (6이 맞는거같은데 추후 물어보고 수정)
    ) {
        memberImageUrls.take(5).forEach { url -> // 현재 5명 제한 걸어둠 추후 이야기 해볼 것
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