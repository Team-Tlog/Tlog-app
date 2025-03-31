package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.SearchBar
import com.tlog.ui.component.TopBar
import com.tlog.ui.theme.BackgroundBlue
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.SearchViewModel


@Preview
@Composable
fun SelectReviewWriteScreen(viewModel: SearchViewModel = viewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopBar(
                text = "리뷰작성"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 14.dp)
                    .background(
                        color = BackgroundBlue,
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                SearchBar(
                    value = viewModel.searchText.value,
                    onValueChange = {
                        viewModel.updateSearchText(it)
                        Log.d("SearchText", viewModel.searchText.value)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "검색",
                            tint = Color(0xFF676767),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                )

            }

            Spacer(modifier = Modifier.height(201.dp))

            if (viewModel.searchText.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                Log.d("여행지 등록", "my click!!")
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_registration),
                            contentDescription = "여행지 등록",
                            modifier = Modifier
                                .size(64.dp)
                                .padding(bottom = 15.dp)
                        )
                        Text(
                            text = "여행지 등록하기",
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontFamily = MainFont,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}