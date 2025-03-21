package com.tlog.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont


@Composable
fun TopFieldBar(
    text: String = "리뷰작성"
    //, onBackClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(top = 42.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // 가로 방향 가운데 정렬
            verticalAlignment = Alignment.CenterVertically // 세로 방향 가운데 정렬
        ) {
//            IconButton(
//                onClick = onBackClick,
//                modifier = Modifier
//                    .size(42.dp)
//                    .padding(10.dp)
//            ) {
//                Icon(                    /*추후 아이콘 생길 시 주석해제*/
//                    painter = painterResource(id = R.drawable.ic_left_arrow),
//                    contentDescription = "go_back",
//                )
//            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                text = text,
                fontSize = 20.sp,
                fontFamily = MainFont,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        }
    }
}