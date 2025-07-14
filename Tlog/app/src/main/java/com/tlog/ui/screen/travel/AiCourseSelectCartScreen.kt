package com.tlog.ui.screen.travel

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.tmp.TmpTravelList
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.tmp.TmpCartViewModel


@Preview
@Composable
fun AiCourseSelectCartScreen(viewModel: TmpCartViewModel = viewModel()) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(37.dp))

            Text(
                text = "가고싶은 여행지를\n선택해주세요!",
                style = BodyTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(46.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 22.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "전체 선택",
                    modifier = Modifier.clickable {
                        viewModel.allChecked()
                        Log.d("all select", "my click!!")
                    },
                    style = Body2Regular,
                    color = MainColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TmpTravelList(
                travelList = viewModel.travelList.value,
                checkedList = viewModel.checkedList.value,
                setCheckBox = { index, checked ->
                    viewModel.updateChecked(index, checked)
                }
            )
        }

        AnimatedVisibility( // 애니메이션
            visible = viewModel.getCheckedTravelList().isNotEmpty(), // 체크된 List가 비어있지 않으면 띄우기
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 15.dp)
        ) {
            MainButton(
                text = "AI 코스 추천받기",
                onClick = {
                    Log.d("AI", "my click!!")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }
    }
}