package com.tlog.ui.component.share

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainFont
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Calendar(
    today: LocalDate
) {
    val year = today.year
    val month = today.monthValue
    val day = today.dayOfMonth

    val pagerState =  rememberPagerState(pageCount = { 5 })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        var pageYear = year
        var pageMonth = month + page
        if (pageMonth > 12) {
            pageYear += 1
            pageMonth -= 12
        }

        val firstDay = LocalDate.of(pageYear, pageMonth, 1)
        val dayOfWeek = firstDay.dayOfWeek.value // 1(월) ~ 7(일)
        val monthLength = YearMonth.of(pageYear, pageMonth).lengthOfMonth()


        // 날짜 설정 부
        val days = mutableListOf<String>()

        repeat(dayOfWeek) { days.add("") } // 첫 주 시작점 밸런스 맞추기 repeat(n) -> 코드를 n번 실행

        for (day in 1..monthLength)
            days.add(day.toString())

        while (days.size % 7 != 0) // 마지막 주 밸런스 맞추기
            days.add("")

        val weeks = days.chunked(7) // 7개씩 쪼개서 리스트로 나누기

        Column {
            Text(
                text = "$pageYear" + "년 " + "$pageMonth" + "월",
                fontFamily = MainFont,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            weeks.forEach { week ->
                WeekRow(week)
            }
        }
    }
}


@Composable
fun WeekRow(
    week: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        week.forEach { day ->
            DayBox(day)
        }
    }
}


@Composable
fun DayBox(
    day: String
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .padding(8.dp)
    ) {
        Text(
            text = day,
            fontFamily = MainFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

