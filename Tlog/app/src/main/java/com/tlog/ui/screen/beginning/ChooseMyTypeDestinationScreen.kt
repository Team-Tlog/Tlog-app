package com.tlog.ui.screen.beginning

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlog.R
import com.tlog.ui.component.share.DestinationCard
import com.tlog.ui.component.share.MainButton
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.beginning.ChooseMyTypeViewModel


@Composable
fun ChooseMyTypeDestinationScreen(
    tbtiValue: String,
    viewModel: ChooseMyTypeViewModel = hiltViewModel()
) {
    val destinations = remember { (1..12).toList() }
    val images = remember { listOf(
            R.drawable.img_travel_ex01,
            R.drawable.img_travel_ex02,
            R.drawable.img_travel_ex03,
            R.drawable.img_travel_ex04,
            R.drawable.img_travel_ex05,
            R.drawable.img_travel_ex06,
            R.drawable.img_travel_ex07,
            R.drawable.img_travel_ex08,
            R.drawable.img_travel_ex09,
            R.drawable.img_travel_ex10,
            R.drawable.img_travel_ex11,
            R.drawable.img_travel_ex12,
        )
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 22.dp,
                end = 22.dp,
                top = 95.dp,
                bottom = 100.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(
                span = { GridItemSpan(2) },
                key = "header"
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "마음에 드는\n여행지 선택",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MainFont
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "여행지 3곳까지 선택할 수 있어요!",
                        fontSize = 15.sp,
                        fontFamily = MainFont,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(85.dp))
                }
            }

            items(
                count = destinations.size,
                key = { idx -> "travel$idx"} ) { idx ->
                val name = destinations[idx]
                val image = images[idx]
                val isSelected = viewModel.selected.value.contains(idx)

                DestinationCard(
                    name = name.toString(),
                    image = image,
                    isSelected = isSelected,
                    onClick = {
                        viewModel.toggleSelection(idx)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 15.dp)
        ) {
            MainButton(
                text = "선택 완료",
                onClick = { viewModel.registerUser(tbtiValue) },
                enabled = viewModel.checkEnabled(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }
    }
}