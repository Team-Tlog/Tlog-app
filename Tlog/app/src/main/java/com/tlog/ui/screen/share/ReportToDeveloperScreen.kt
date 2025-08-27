package com.tlog.ui.screen.share

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.R
import com.tlog.ui.component.share.BottomLineInputField
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.ReportViewModel

@Preview(showBackground = true)
@Composable
fun ReportToDeveloperScreen(
    viewModel: ReportViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (10.5).dp, start = 24.dp, end = 15.dp)
        ) {
            Text(
                text = "피드백",
                style = TextStyle(
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_send),
                contentDescription = "피드백 전송하기",
                tint = if (viewModel.checkInput()) MainColor else Color.Unspecified,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (viewModel.checkInput()) {
                            Log.d("report", "my click!!")
                            // api 보내기
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "제목",
                style = TextStyle(
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            BottomLineInputField(
                value = viewModel.title.value,
                onValueChange = {
                    viewModel.updateTitle(it)
                },
                placeholder = "입력해주세요"
            )

            Spacer(modifier = Modifier.height(29.dp))

            BottomLineInputField(
                value = viewModel.content.value,
                onValueChange = {
                    viewModel.updateContent(it)
                },
                placeholder = "내용을 입력해주세요",
                singleLine = false,
                modifier = Modifier
                    .height(117.dp)
            )
        }
    }
}