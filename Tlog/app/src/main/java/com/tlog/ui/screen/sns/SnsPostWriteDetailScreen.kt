package com.tlog.ui.screen.sns

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tlog.data.model.response.course.CourseItem
import com.tlog.ui.component.share.TextButtonTopBar
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.sns.SnsPostViewModel

@Composable
fun SnsPostWriteDetailScreen(
    viewModel: SnsPostViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    val courseIdx = viewModel.selectedCourse.value
    val recentCourses by viewModel.recentTravelCourses.collectAsState()

    val selectedCourse = recentCourses.getOrNull(courseIdx)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }
    }

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
                viewModel.postWrite(context)

                Toast.makeText(context, "게시물 등록 성공", Toast.LENGTH_SHORT).show()

                navController.popBackStack()
                navController.popBackStack()
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        SelectedCourse(selectedCourse!!)

        Spacer(modifier = Modifier.height(30.dp))

        CoursePictures(viewModel.selectImages.value)

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
    selectedCourse: CourseItem
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
            AsyncImage(
                model = selectedCourse.dates.first().destinationGroups.first().destinations.first().imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Text(
            text = selectedCourse.dates.first().destinationGroups.first().destinations.first().city,
            style = Body1Regular
        )
    }
}

@Composable
fun CoursePictures(
    pictureList: List<Uri>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(
            count = pictureList.size,
            key = { idx -> idx } // pictureList가 삭제되거나 수정되지 않음 -> idx를 키로 사용해도 무관
        ) { idx ->
            Box(
                modifier = Modifier
                    .width(94.dp)
                    .height(105.dp)
            ) {
                AsyncImage(
                    model =pictureList[idx],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}