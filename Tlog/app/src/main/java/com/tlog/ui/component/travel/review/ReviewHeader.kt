package com.tlog.ui.component.travel.review

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont


@Composable
fun ReviewHeader(
    reviewCnt: Int,
    reviewWrite: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "리뷰",
            style = TextStyle(
                fontFamily = MainFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = reviewCnt.toString(),
            style = TextStyle(
                fontFamily = MainFont,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5F5F5F)
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_review_write),
            contentDescription = "리뷰 쓰기",
            tint = Color.Unspecified,
            modifier = Modifier
                .clickable {
                    reviewWrite()
                }
        )
    }
}