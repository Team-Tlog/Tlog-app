package com.tlog.ui.screen.review

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tlog.R
import com.tlog.ui.component.share.BottomLineInputField
import com.tlog.ui.component.share.HashtagInputGroup
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.component.share.PhotoUploadBox
import com.tlog.ui.component.share.TopBar
import com.tlog.ui.component.share.TwoColumnRadioGroup
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.travel.AddTravelViewModel


@Composable
fun AddTravelScreen(
    viewModel: AddTravelViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddTravelViewModel.UiEvent.ApiSuccess -> {
                    navController.navigate("main") {
                        viewModel.clearImages()
                        viewModel.clearHashTags()
                        Toast.makeText(context, "여행지 등록 성공", Toast.LENGTH_SHORT).show()
                        popUpTo("main") { inclusive = false }
                    }
                }
                is AddTravelViewModel.UiEvent.ApiError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.systemBars)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal =  24.dp)
        ) {
            TopBar(
                text = "여행지 등록"
            )

            Spacer(modifier = Modifier.height(41.dp))


            Text(
                text = "여행지명",
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(12.dp))

            BottomLineInputField(
                value = viewModel.travelName.value,
                onValueChange = { viewModel.updateTravelName(it) },
                placeholder = "입력해주세요",
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "주소",
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
            )
            BottomLineInputField(
                value = viewModel.travelAddress.value,
                onValueChange = { viewModel.updateTravelAddress(it) },
                placeholder = "검색어를 입력하세요",
                singleLine = true,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "",
                        tint = Color(0xFF676767)
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            TwoColumnRadioGroup(
                title = "주차 가능여부",
                options = listOf("가능", "불가능"),
                selectedOption = if (viewModel.hasParking.value) "가능" else "불가능",
                onOptionSelected = {
                    viewModel.updateHasParking(it == "가능")
                }
            )

            TwoColumnRadioGroup(
                title = "반려견 가능여부",
                options = listOf("가능", "불가능"),
                selectedOption = if (viewModel.isPetFriendly.value) "가능" else "불가능",
                onOptionSelected = {
                    viewModel.updateIsPetFriendly(it == "가능")
                }
            )

            HashtagInputGroup(
                value = viewModel.hashTag.value,
                placeholderText = "입력해주세요",
                hashTags = viewModel.hashTags.value,
                onValueChange = { viewModel.updateHashTag(it) },
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

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "설명글",
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(12.dp))

            BottomLineInputField(
                value = viewModel.travelDescription.value,
                onValueChange = { viewModel.updateTravelDescription(it) },
                placeholder = "입력해주세요",
                singleLine = false,
                modifier = Modifier
                    .height(117.dp)
            )

            Spacer(modifier = Modifier.height(35.dp))

            val imagePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri ->
                uri?.let {
                    viewModel.addImage(it)
                }
            }

            PhotoUploadBox(
                images = if (viewModel.imageUri.value == Uri.EMPTY) emptyList() else listOf(
                    viewModel.imageUri.value
                ),
                maxImageCnt = 1,
                onAddClick = {
                    imagePickerLauncher.launch("image/*")
                }
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        MainButton(
            text = "여행지 등록하기",
            onClick = {
                viewModel.addNewTravel(context)
            },
            enabled = viewModel.checkInput(),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp)
        )

    }
}
