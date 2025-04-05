package com.tlog.ui.screen.review

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.share.HashtagInputGroup
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.MainInputField
import com.tlog.ui.component.share.PhotoUploadBox
import com.tlog.ui.component.share.StarRating
import com.tlog.ui.component.share.TopBar
import com.tlog.viewmodel.review.ReviewViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.tlog.R
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainColor


@Preview
@Composable
fun ReviewWritingScreen(viewModel: ReviewViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    var showHelp by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            TopBar(
                text = "리뷰작성"
            )

            Spacer(modifier = Modifier.height(31.5.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "남산타워는 어떠셨나요?",
                style = BodyTitle,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(22.dp))

            StarRating(
                rating = viewModel.starCnt.value,
                onStarClicked = {
                    viewModel.updateStarCnt(it)
                    Log.d("starCnt", viewModel.starCnt.value.toString())
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            MainInputField( // 안내 메시지 추가 필요
                text = "리뷰작성",
                value = viewModel.review.value,
                onValueChange = { viewModel.updateReview(it) },
                placeholderText = "입력해주세요",
                showHelpPopup = showHelp,
                onDismissHelpPopup = { showHelp = false },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_information),
                        contentDescription = "도움말",
                        tint = MainColor,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { showHelp = true }
                    )
                }
            )

            Spacer(modifier = Modifier.height(26.dp))

            PhotoUploadBox(
                images = viewModel.imageList.value,
                onAddClick = {
                    Log.d("addImage", "my click!!")
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            HashtagInputGroup(
                value = viewModel.hashTag.value,
                placeholderText = "입력해주세요",
                hashTags = viewModel.hashTags.value,
                onValueChange = { viewModel.updateHashTag(it) },
                onAddHashtag = { viewModel.addHashTag(it) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (viewModel.hashTag.value.isNotBlank()) {
                            viewModel.addHashTag(viewModel.hashTag.value.trim())
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
                    .height(70.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
            )
        }
    }
}