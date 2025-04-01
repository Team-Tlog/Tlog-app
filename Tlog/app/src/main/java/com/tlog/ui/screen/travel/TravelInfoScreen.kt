package com.tlog.ui.screen.travel

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.data.model.travel.TravelInfoTopBar
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.travel.TravelInfoViewModel


@Preview
@Composable
fun TravelInfoScreen(viewModel: TravelInfoViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(319.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tmp_jeju), // 너 이미지
                    contentDescription = "여행지 샤진",
                    contentScale = ContentScale.Crop, // 비율 맞춰 채우기
                    modifier = Modifier.matchParentSize() // Box 크기에 맞춰줌!
                )

                TravelInfoTopBar(
                    iconList = listOf(R.drawable.ic_heart)
                )
            }

            Box( // 박스의 범위도 고민되고 이 방식이 맞나도 고민됨 일단 여기선 여행지 이름, 위치, 별점, 해시태그까지 박스
                modifier = Modifier
                    .offset(y = (-79).dp)
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .padding(top = 30.dp, start = (31.5).dp, end = (31.5).dp)
            ) {
                Column {
                    Text(
                        text = viewModel.selectedTravelInfo.value.travelName,
                        fontFamily = MainFont,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Box {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_map_pin),
                                contentDescription = "위치 ${viewModel.selectedTravelInfo.value.cityName}",
                                tint = Color.Unspecified
                            )

                            Text(
                                text = viewModel.selectedTravelInfo.value.cityName,
                                style = Body2Regular
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_filled_star),
                                contentDescription = "위치 ${viewModel.selectedTravelInfo.value.cityName}",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(14.dp)
                            )

                            Text(
                                text = viewModel.selectedTravelInfo.value.starRatings.avgStarRating.toString(),
                                style = Body2Regular
                            )

                            Text(
                                text = "(20)", // 추후 api명세서 나오면 제대로 수정하기
                                style = Body2Regular
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    HashTagsGroup(
                        hashTags =  viewModel.selectedTravelInfo.value.hashTags,
                        space = 4.dp
                    )

                    Spacer(modifier = Modifier.height(57.dp))

                    Text(
                        text = viewModel.selectedTravelInfo.value.description,
                        style = Body1Regular
                    )
                }
            }


        }
    }
}