package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.DropDownBox
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.MainInputField
import com.tlog.ui.component.TwoColumnRadioGroup
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.UserInfoViewModel


@Preview
@Composable
fun UserInfoInputScreen(viewModel: UserInfoViewModel = viewModel()) {
    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("남성", "여성")
    val petOption = listOf("있음", "없음")
    val travelOption = listOf("가족여행", "홀로여행", "우정여행", "커플여행")
    val carOption = listOf("있음", "없음")

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = "추가 정보를 입력해주세요",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = MainFont,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .padding(start = 30.dp)
                )

                Spacer(modifier = Modifier.height(38.dp))
                
                MainInputField(
                    text = "닉네임",
                    value = viewModel.nickname.value,
                    onValueChange = {
                        viewModel.updateNickname(it)
                        Log.d("nickname", it)
                    },
                    placeholderText = "입력해주세요" // hint
                )

                Spacer(modifier = Modifier.height(21.dp))

                Text(
                    text = "성별",
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 24.dp)
                )

                Spacer(modifier = Modifier.height(21.dp))

                DropDownBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !(expanded) },
                    options = genderOptions,
                    value = viewModel.gender.value,
                    onOptionSelected = {
                        viewModel.updateGender(it)
                        Log.d("genderOption", viewModel.gender.value)
                    },
                    modifier = Modifier
                        .background(Color.White)
                        .padding(start = 24.dp)
                        .width(95.dp)
                        .shadow(3.dp, shape = RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.height(21.dp))

                TwoColumnRadioGroup(
                    title = "반려견 여부",
                    options = petOption,
                    selectedOption = if (viewModel.hasPet.value) petOption[0] else petOption[1],
                    onOptionSelected = {
                        viewModel.updatePet(if (it == petOption[0]) true else false)
                        Log.d("petOption", viewModel.hasPet.toString())
                    }
                )


                TwoColumnRadioGroup(
                    title = "즐겨하는 여행 타입",
                    options = travelOption,
                    selectedOption = viewModel.travelType.value,
                    onOptionSelected = {
                        viewModel.updateTravelType(it)
                        Log.d("travelOption", viewModel.travelType.value)
                    }
                )


                TwoColumnRadioGroup(
                    title = "자차유무",
                    options = carOption,
                    selectedOption = if (viewModel.hasCar.value) carOption[0] else carOption[1],
                    onOptionSelected = {
                        viewModel.updateCar(if (it == carOption[0]) true else false)
                        Log.d("carOption", viewModel.hasCar.toString())

                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "추후 변경이 가능합니다.",
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFA8A8A8),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(13.dp))

                MainButton(
                    text = "Tlog 시작하기",
                    onClick = { Log.d("startButton", "my click!!")},
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}