package com.tlog.ui.screen.sns

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.data.model.sns.TravelCourse
import com.tlog.ui.component.share.TextButtonTopBar
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.sns.SnsPostViewModel

@Preview(showBackground = true)
@Composable
fun SnsPostWriteDetailScreen(
    viewModel: SnsPostViewModel = viewModel()
) {
    val courseIdx = viewModel.selectedCourse.value
    val selectedCourse = viewModel.recentTravelCourses.value[courseIdx]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        TextButtonTopBar(
            title = "게시글 작성",
            btnText = "완료",
            btnClickable = {
                Log.d("게시글 작성 완료", "my click!!")
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        SelectedCourse(selectedCourse)

        Spacer(modifier = Modifier.height(30.dp))

        CoursePictures(selectedCourse)

        HorizontalDivider(
            color = Color(0xFFF4F4F4),
            thickness = 1.dp,
            modifier = Modifier
                .padding(start = 13.dp, end = 13.dp, top = 25.dp, bottom = 19.dp)
        )

        TextField(
            value = viewModel.postContent.value,
            onValueChange = {
                viewModel.updatePostContent(it)
            },
            placeholder = {
                Text(
                    text = "게시물 내용 쓰기",
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.Black
                    ),
                )
            },
            textStyle = TextStyle(
                fontFamily = MainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = Color.Black
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(Color.White)
                .height(200.dp)
        )
    }
}

@Composable
fun SelectedCourse(
    selectedCourse: TravelCourse
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(MainColor) // 크기 체크 용임
        ) {
            Image(
                painter = painterResource(selectedCourse.pictureList.first()),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Text(
            text = selectedCourse.city,
            style = Body1Regular
        )
    }
}

@Composable
fun CoursePictures(
    selectedCourse: TravelCourse
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(selectedCourse.pictureList.size) { index ->
            Box(
                modifier = Modifier
                    .width(94.dp)
                    .height(105.dp)
            ) {
                Image(
                    painter = painterResource(selectedCourse.pictureList[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}