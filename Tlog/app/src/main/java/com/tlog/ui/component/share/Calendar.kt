package com.tlog.ui.component.share

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Calendar(
    today: LocalDate,
    startDate: LocalDate?,
    endDate: LocalDate?,
    updateDateRange: (LocalDate) -> Unit
) {
    val year = today.year
    val month = today.monthValue

    val selectedDates = if (startDate != null && endDate != null) {
        generateSequence(startDate) { it.plusDays(1) }
            .takeWhile { !it.isAfter(endDate) }
            .toSet()
    } else emptySet()


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
        val days = mutableListOf<LocalDate?>()

        if (dayOfWeek != 7)
            repeat(dayOfWeek) { days.add(null) } // 첫 주 시작점 밸런스 맞추기 repeat(n) -> 코드를 n번 실행

        for (day in 1..monthLength)
            days.add(LocalDate.of(pageYear, pageMonth, day))

        while (days.size % 7 != 0) // 마지막 주 밸런스 맞추기
            days.add(null)

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
                WeekRow(
                    week = week,
                    startDate = startDate,
                    endDate = endDate,
                    selectedDates = selectedDates,
                    onClick = { updateDateRange(it) }
                )
            }
        }
    }
}


@Composable
fun WeekRow(
    week: List<LocalDate?>,
    startDate: LocalDate?,
    endDate: LocalDate?,
    selectedDates: Set<LocalDate>,
    onClick: (LocalDate) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        week.forEach { date ->
            DayBox(
                date = date,
                startDate = startDate,
                endDate = endDate,
                selectedDates = selectedDates,
                onClick = { date?.let { onClick(it) } }
            )
        }
    }
}


@Composable
fun DayBox(
    date: LocalDate?,
    startDate: LocalDate?,
    endDate: LocalDate?,
    selectedDates: Set<LocalDate>,
    onClick: () -> Unit
) {

    val selected = date != null && (selectedDates.contains(date) || date == startDate)
    val isStart = date == startDate
    val isEnd = date == endDate

    val shape = when {
        isStart && isEnd -> CircleShape
        isStart && endDate == null -> CircleShape
        isStart -> RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
        isEnd -> RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
        selected -> RectangleShape
        else -> RectangleShape
    }

    Box(
        modifier = Modifier
            .size(38.dp)
            .clickable { if (date != null && !date.isBefore(LocalDate.now())) onClick() }
            .padding(vertical = 2.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (selected) MainColor.copy(alpha = 0.3f) else Color.Transparent,
                    shape = shape
                ),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = date?.dayOfMonth?.toString() ?: "",
                fontFamily = MainFont,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = when {
                    date == null -> Color.Transparent
                    date == LocalDate.now() -> MainColor
                    LocalDate.now().isBefore(date) -> Color.Black
                    else -> Color.Gray.copy(alpha = 0.5f)
                },
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

