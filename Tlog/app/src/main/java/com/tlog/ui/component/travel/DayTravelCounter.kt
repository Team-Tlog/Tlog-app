package com.tlog.ui.component.travel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont
import java.time.LocalDate


@Composable
fun DayTravelCounter(
    idx: Int,
    travelCnt: Int,
    minusClick: (LocalDate, Int) -> Unit,
    plusClick: (LocalDate, Int) -> Unit,
    date: LocalDate
) {
    val year = date.year
    val month = date.monthValue
    val day = date.dayOfMonth
    val dateStr = '(' + year.toString() + '.' + month.toString() + '.' + day.toString() + ')'

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "DAY $idx",
            fontFamily = MainFont,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.width(11.dp))

        Text(
            text = dateStr,
            fontFamily = MainFont,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_minus),
            contentDescription = "Day $idx 여행지 빼기 버튼",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(20.dp)
                .clickable { minusClick(date, if (travelCnt != 0) travelCnt - 1 else 0) }
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = travelCnt.toString(),
            fontFamily = MainFont,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            painter = painterResource(R.drawable.ic_plus),
            contentDescription = "Day $idx 여행지 추가하기 버튼",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(20.dp)
                .clickable { plusClick(date, travelCnt + 1) }
        )
    }
}