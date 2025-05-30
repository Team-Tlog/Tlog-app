package com.tlog.ui.screen.review

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlog.ui.component.share.HashtagInputGroup
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.MainInputField
import com.tlog.ui.component.share.PhotoUploadBox
import com.tlog.ui.component.share.StarRating
import com.tlog.ui.component.share.TopBar
import com.tlog.viewmodel.review.ReviewViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.R
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.review.ReviewViewModel.UiEvent


//@Preview
@Composable
fun ReviewWritingScreen(
    viewModel: ReviewViewModel = hiltViewModel(),
    travelId: String,
    travelName: String, // id로 api 호출하기엔 이름만 필요해서 이렇게 하고 등록할 때 id 이용해서 등록하는게 좋을 것 같습니다
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    var showHelp by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initUserId(context) // user Id 가져오기 -> cart에도 이 방식으로 수정하기 !!

        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ReviewSuccess -> {
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = false } // 메인 화면을 제외하고 모두 제거
                    }
                }
                is UiEvent.ReviewError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
                text = travelName + (if(hasJongseong(travelName)) "은" else "는") + " 어떠셨나요?",
                style = BodyTitle,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(22.dp))

            StarRating(
                rating = viewModel.rating.value,
                onStarClicked = {
                    viewModel.updateRating(it)
                    Log.d("starCnt", viewModel.rating.value.toString())
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

            val imagePickerLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
                contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
            ) { uri ->
                uri?.let {
                    viewModel.addImage(it)
                }
            }

            PhotoUploadBox(
                images = viewModel.imageList.value,
                onAddClick = {
                    imagePickerLauncher.launch("image/*")
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
                onClick = {
                    viewModel.addReview(context = context, travelId = travelId)
                },
                modifier = Modifier
                    .height(70.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
            )
        }
    }
}

fun hasJongseong(text: String): Boolean {
    if (text.isEmpty()) return false

    val lastChar = if (text.last() == ')' && text.length >= 2) text[text.length - 2] else text.last()

    if (lastChar !in '\uAC00'..'\uD7A3') return false

    val jongseongIndex = (lastChar.code - 0xAC00) % 28
    return jongseongIndex != 0
}