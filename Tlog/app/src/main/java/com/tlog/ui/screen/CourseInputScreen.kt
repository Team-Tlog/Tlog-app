package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.data.local.RegionData
import com.tlog.ui.component.TwoColumnRadioGroup
import com.tlog.ui.component.share.Calendar
import com.tlog.ui.component.share.DropDown
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.CourseInputViewModel
import java.time.LocalDate


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
                fontFamily = MainFont,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "지역",
                fontFamily = MainFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row {
                DropDown(
                    options = RegionData.regionMap.keys.toList(),
                    value = viewModel.city.value,
                    valueChange = {
                        viewModel.updateCity(it)
                        Log.d("city", it)
                    }
                )

                Spacer(modifier = Modifier.width(24.dp))

                // 시군구 필드
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


            Calendar(LocalDate.now())
        }
    }
}