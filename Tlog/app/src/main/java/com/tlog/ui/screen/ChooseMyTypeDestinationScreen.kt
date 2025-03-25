package com.tlog.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.component.DestinationCard
import com.tlog.ui.component.MainButton
import com.tlog.ui.theme.MainFont
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.viewmodel.ChooseMyTypeViewModel


@Preview(showBackground = true)
@Composable
fun ChooseMyTypeDestinationScreen(
    viewModel: ChooseMyTypeViewModel = viewModel()
) {
    val maxSelection = 3
    val destinations = listOf("놀이공원", "공원", "호수", "바다", "대전", "광주", "대구", "부산", "충남", "충북", "전남", "전북", "경북", "경남", "울산", "제주")
    val images = List(destinations.size) { R.drawable.destination_img }

    val selected = viewModel.selected.value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 45.dp)
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(137.dp))

            Text(
                text = "마음에 드는\n여행지 선택",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = MainFont
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "여행지 3곳까지 선택할 수 있어요!",
                fontSize = 15.sp,
                fontFamily = MainFont,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(85.dp))

            for (i in destinations.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (j in 0..1) {
                        val index = i + j
                        if (index < destinations.size) {
                            val name = destinations[index]
                            val image = images[index]
                            val isSelected = selected.contains(name)

                            DestinationCard(
                                name = name,
                                image = image,
                                isSelected = isSelected,
                                onClick = {
                                    viewModel.toggleSelection(name, maxSelection)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(6.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
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
                onClick = { /* 선택 완료 처리*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }
    }
}