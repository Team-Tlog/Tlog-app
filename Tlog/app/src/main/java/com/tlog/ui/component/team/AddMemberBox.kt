package com.tlog.ui.component.team

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont

@Composable
fun AddMemberBox(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(31.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0x666792EF))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = "팀원초대",
                fontFamily = MainFont,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                painter = painterResource(R.drawable.ic_filled_add_circle),
                contentDescription = "팀원추가",
                tint = Color.White,
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }

}