package com.tlog.ui.screen.beginning

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.MainInputField
import com.tlog.ui.component.share.TwoColumnRadioGroup
import com.tlog.ui.component.share.DropDown
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.beginning.UserInfoViewModel


@Preview
@Composable
fun UserInfoInputScreen(viewModel: UserInfoViewModel = viewModel()) {
    val genderOptions = listOf("남성", "여성")
    val petOption = listOf("있음", "없음")
    val travelOption = listOf("가족여행", "홀로여행", "우정여행", "커플여행")
    val carOption = listOf("있음", "없음")

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        color = Color.White
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 21.dp)
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 3.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "추가 정보를 입력해주세요",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = MainFont,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = 35.dp)
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
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(21.dp))

                DropDown(
                    options = genderOptions,
                    value = viewModel.gender.value,
                    valueChange = {
                        viewModel.updateGender(it)
                    }
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
                )

                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}