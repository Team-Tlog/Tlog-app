package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.SearchBar
import com.tlog.ui.component.TopBar
import com.tlog.ui.theme.BackgroundBlue
import com.tlog.viewmodel.SearchViewModel


@Preview
@Composable
fun SelectReviewWriteScreen(viewModel: SearchViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
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
        }
    }
}