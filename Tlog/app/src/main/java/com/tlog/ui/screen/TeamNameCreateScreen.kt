package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.HashtagInputGroup
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.MainInputField
import com.tlog.ui.component.PhotoUploadBox
import com.tlog.ui.component.TeamNameInputField
import com.tlog.ui.component.TopBar
import com.tlog.ui.component.TwoColumnRadioGroup
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.AddTravelViewModel
import com.tlog.viewmodel.TeamNameViewModel


@Preview
@Composable
fun TeamNameCreateScreen(viewModel: TeamNameViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)

    ) {
        TopBar(
            text = "팀 생성"
        )

        Spacer(modifier = Modifier.height(35.dp))

        TeamNameInputField(
            text = "팀 이름을 정해주세요!",
            value = viewModel.TeamName.value,
            onValueChange = {
                viewModel.updateTeamName(it)
                Log.d("TeamNameValue", viewModel.TeamName.value)
            },
            placeholderText = "입력해주세요",

        )


        Spacer(modifier = Modifier.weight(1f))

        // 입력값이 있을 때만 버튼 표시
        if (viewModel.TeamName.value.isNotEmpty()) {
            MainButton(
                text = "팀 생성하기",
                onClick = { Log.d("Team Create", "my click!!") },
                modifier = Modifier
                    .height(70.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
            )
        }
    }
}