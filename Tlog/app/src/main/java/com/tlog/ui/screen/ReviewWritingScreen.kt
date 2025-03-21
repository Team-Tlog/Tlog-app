package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.HashtagInputGroup
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.MainInputField
import com.tlog.ui.component.PhotoUploadBox
import com.tlog.ui.component.StarRating
import com.tlog.ui.component.TopBar
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.ReviewViewModel


@Preview
@Composable
fun ReviewWritingScreen(viewModel: ReviewViewModel = viewModel()) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            TopBar(
                text = "리뷰작성"
            )

            Spacer(modifier = Modifier.height(31.5.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "남산타워는 어떠셨나요?",
                fontFamily = MainFont,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(22.dp))

            StarRating(
                rating = viewModel.starCnt,
                onStarClicked = {
                    viewModel.updateStarCnt(it)
                    Log.d("starCnt", viewModel.starCnt.toString())
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            MainInputField(
                text = "리뷰작성",
                value = viewModel.review,
                onValueChange = { viewModel.updateReview(it) },
                placeholderText = "입력해주세요",
                singleLine = false,
                modifier = Modifier.height(117.dp)
            )

            Spacer(modifier = Modifier.height(26.dp))

            PhotoUploadBox(
                images = viewModel.imageList,
                onAddClick = {
                    Log.d("addImage", "my click!!")
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            HashtagInputGroup(
                value = viewModel.hashTag,
                placeholderText = "입력해주세요",
                hashTags = viewModel.hashTags,
                onValueChange = { viewModel.updateHashTag(it) },
                onAddHashtag = { viewModel.addHashTag(it) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (viewModel.hashTag.isNotBlank()) {
                            viewModel.addHashTag(viewModel.hashTag.trim())
                            viewModel.updateHashTag("")
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            MainButton(
                text = "리뷰 등록하기",
                onClick = { Log.d("addReview", "my click!!") },
                modifier = Modifier
                    .height(55.dp)
                    .padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(68.dp))


        }
    }
}