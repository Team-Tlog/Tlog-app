package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.HashtagInputSection
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.MainInputField
import com.tlog.ui.component.PhotoUploadBox
import com.tlog.ui.component.TwoColumnRadioGroup
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.AddTravelViewModel


@Preview
@Composable
fun AddTravelDestinationScreen(viewModel: AddTravelViewModel = viewModel()) {
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(top = 30.dp)
    ) {
        MainInputField(
            text = "여행지명",
            value = viewModel.travelName,
            onValueChange = { viewModel.updateTravelName(it) },
            placeholderText = "입력해주세요"
        )

        Spacer(modifier = Modifier.height(15.dp))

        MainInputField(
            text = "주소",
            value = viewModel.travelAddress,
            onValueChange = { viewModel.updateTravelAddress(it) },
            placeholderText = "지도로 검색하기",
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_circle),
                    tint = MainColor.copy(alpha = 0.3f),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            Log.d("addAddress", "my click!!")
                        }
                )
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        TwoColumnRadioGroup(
            title = "주차 가능여부",
            options = listOf("가능", "불가능"),
            selectedOption = if (viewModel.hasParking) "가능" else "불가능",
            onOptionSelected = {
                viewModel.updateHasParking(it == "가능")
                Log.d("travelOption", viewModel.hasParking.toString())
            }
        )

        TwoColumnRadioGroup(
            title = "반려견 가능여부",
            options = listOf("가능", "불가능"),
            selectedOption = if (viewModel.isPetFriendly) "가능" else "불가능",
            onOptionSelected = {
                viewModel.updateIsPetFriendly(it == "가능")
                Log.d("travelOption", viewModel.isPetFriendly.toString())
            }
        )

        HashtagInputSection(
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

        Spacer(modifier = Modifier.height(15.dp))

        MainInputField(
            text = "설명글",
            value = viewModel.travelDescription,
            onValueChange = { viewModel.updateTravelDescription(it) },
            placeholderText = "입력해주세요",
            singleLine = false,
            modifier = Modifier.height(130.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        PhotoUploadBox( // 추후 사진을 어떻게 크기를 줄일지 생각해보면 좋을 듯 성능적으로도, 우리 용량적으로도
            images = viewModel.imageList,
            onAddClick = {
                Log.d("PhotoUpload", "my click!!")
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            text = "여행지 등록하기",
            onClick = {
                Log.d("addTravel", "my click!!")
                viewModel.clearImages()
                viewModel.clearHashTags()
            },
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp)
        )

    }
}