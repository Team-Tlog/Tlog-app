package com.tlog.ui.screen.travel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.data.local.RegionData
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.TwoColumnRadioGroup
import com.tlog.ui.component.share.Calendar
import com.tlog.ui.component.share.DropDown
import com.tlog.ui.component.share.DropDownCheckBox
import com.tlog.ui.component.travel.DayTravelCounter
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.BackgroundBlue
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.travel.CourseInputViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@Preview
@Composable
fun CourseInputScreen(viewModel: CourseInputViewModel = viewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .background(Color.White)
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "이것만 알려주세요!",
                style = BodyTitle
            )

            Spacer(modifier = Modifier.height(30.dp))
 
            Text(
                text = "지역",
                fontFamily = MainFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row ( 
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                DropDown(
                    options = RegionData.regionMap.keys.toList(),
                    value = viewModel.city.value,
                    selectedTextStyle = TextStyle(
                        fontFamily = MainFont,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainColor
                    ),
                    textStyle = TextStyle(
                        fontFamily = MainFont,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = MainColor
                    ),
                    bgColor = BackgroundBlue,
                    dividerColor = Color.White,
                    iconColor = MainColor,
                    valueChange = {
                        viewModel.updateCity(it)
                        Log.d("city", it)
                    },
                    modifier = Modifier
                        .heightIn(max = 185.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .verticalScroll(rememberScrollState())
                )

                // 시군구 필드
                DropDownCheckBox(
                    city = viewModel.city.value,
                    options = RegionData.regionMap[viewModel.city.value] ?: emptyList(),
                    value = viewModel.district.value,
                    checkedSet = viewModel.checkedDistrict.value,
                    onClick = {
                        if (it in viewModel.city.value + viewModel.checkedDistrict.value)
                            viewModel.deleteCheckedDistrict(it)
                        else
                            viewModel.updateCheckedDistrict(it)
                    },
                    modifier = Modifier
                        .width(202.dp)
                        .heightIn(max = 285.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            TwoColumnRadioGroup(
                title = "반려견 유무",
                options = listOf("있음", "없음"),
                selectedOption = if(viewModel.hasPet.value) "있음" else "없음",
                onOptionSelected = {
                    viewModel.updatePet(if (it == "있음") true else false)
                }
            )

            TwoColumnRadioGroup(
                title = "이동수단",
                options = listOf("있음", "없음"),
                selectedOption = if(viewModel.hasCar.value) "있음" else "없음",
                onOptionSelected = {
                    viewModel.updateCar(if (it == "있음") true else false)
                }
            )

            Text(
                text = "여행일정",
                fontFamily = MainFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Calendar(
                today = LocalDate.now(),
                viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "방문 여행지 개수 설정",
                fontFamily = MainFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))


            val travelDates = viewModel.getTravelDates()

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                travelDates.forEach { day ->
                    DayTravelCounter(
                        idx = ChronoUnit.DAYS.between(
                            viewModel.startDate.value,
                            day
                        ).toInt() + 1,
                        travelCnt = viewModel.travelCountByDate.value[day] ?: 0,
                        minusClick = {date, count -> viewModel.updatePlaceCount(date, count)},
                        plusClick = {date, count -> viewModel.updatePlaceCount(date, count)},
                        date = day
                    )
                }
            }

            Spacer(modifier = Modifier.height(69.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "추후 변경이 가능합니다.",
                fontFamily = MainFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFA8A8A8),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            ) {
                MainButton(
                    text = "다음",
                    onClick = {
                        Log.d("course next button", "my click!!")
                    },
                    modifier = Modifier
                        .height(55.dp)
                )
            }

        }
    }
}