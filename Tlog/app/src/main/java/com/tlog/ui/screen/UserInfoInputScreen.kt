package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.tlog.viewmodel.UserInfoViewModel


@Preview
@Composable
fun UserInfoInputScreen(viewModel: UserInfoViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("남성", "여성")
    val petOption = listOf("있음", "없음")
    val travelOption = listOf("가족여행", "홀로여행", "우정여행", "커플여행")
    val carOption = listOf("있음", "없음")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        BoxWithConstraints (
            modifier = Modifier
                .fillMaxSize()
        ) {
            val topPadding = maxHeight * 0.08f
            val sidePadding = maxWidth * 0.08f
            val textTopPadding = maxHeight * 0.03f
            val standardPadding = maxHeight * 0.025f
            val genderLeftPadding = maxWidth * 0.67f

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "추가 정보를 입력해주세요",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = topPadding)
                        .padding(start = 30.dp)
                )

                Spacer(modifier = Modifier.height(textTopPadding))

                Text(
                    text = "닉네임",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 30.dp)
                )

                Spacer(modifier = Modifier.height(standardPadding))

                MainInputField(
                    value = viewModel.nickname,
                    onValueChange = {
                        viewModel.updateNickname(it)
                        Log.d("nickname", it)
                    },
                    placeholder = { Text("입력해주세요") }, // hint
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = sidePadding)
                )

                Spacer(modifier = Modifier.height(standardPadding))

                Text(
                    text = "성별",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = sidePadding)
                )

                Spacer(modifier = Modifier.height(standardPadding))

                DropDownBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !(expanded) },
                    options = genderOptions,
                    value = viewModel.gender,
                    onOptionSelected = {
                        viewModel.updateGender(it)
                        Log.d("genderOption", viewModel.gender)
                    },
                    modifier = Modifier
                        .background(Color.White)
                        .padding(start = sidePadding, end = genderLeftPadding)
                        .clip(RoundedCornerShape(24.dp))
                )

                Spacer(modifier = Modifier.height(standardPadding))

                Text(
                    text = "반려견 여부",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = sidePadding)
                )

                Spacer(modifier = Modifier.height(standardPadding))

                TwoColumnRadioGroup(
                    options = petOption,
                    selectedOption = if (viewModel.hasPet) petOption[0] else petOption[1],
                    onOptionSelected = {
                        viewModel.updatePet(if (it == petOption[0]) true else false)
                        Log.d("petOption", viewModel.hasPet.toString())
                    }
                )

                Text(
                    text = "즐겨하는 여행 타입",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 30.dp)
                )

                Spacer(modifier = Modifier.height(standardPadding))

                TwoColumnRadioGroup(
                    options = travelOption,
                    selectedOption = viewModel.travelType,
                    onOptionSelected = {
                        viewModel.updateTravelType(it)
                        Log.d("travelOption", viewModel.travelType)
                    }
                )


                Text(
                    text = "자차유무",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 30.dp)
                )

                Spacer(modifier = Modifier.height(standardPadding))

                TwoColumnRadioGroup(
                    options = carOption,
                    selectedOption = if (viewModel.hasCar) carOption[0] else carOption[1],
                    onOptionSelected = {
                        viewModel.updateCar(if (it == carOption[0]) true else false)
                        Log.d("carOption", viewModel.hasCar.toString())

                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "추후 변경이 가능합니다.",
                    color = Color(0xFFA8A8A8),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(15.dp))

                MainButton(
                    text = "Tlog 시작하기",
                    onClick = { Log.d("startButton", "my click!!")},
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, bottom = 40.dp)
                        .height(65.dp)
                )

            }


        }
    }
}