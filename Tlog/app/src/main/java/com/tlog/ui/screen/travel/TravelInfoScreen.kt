package com.tlog.ui.screen.travel

import android.content.ClipData.Item
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.travel.TravelInfoTopBar
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.component.travel.ReviewList
import com.tlog.ui.component.travel.ReviewStar
import com.tlog.ui.component.travel.SimilarTravelSpots
import com.tlog.ui.component.travel.reviewProgressBar
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.style.Body1Regular
import com.tlog.ui.style.Body2Regular
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.style.Title
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.travel.TravelInfoViewModel


@Preview
@Composable
fun TravelInfoScreen(viewModel: TravelInfoViewModel = viewModel()) {
    val totalReviewCount = viewModel.selectedTravelInfo.value.starRatings.sum()
    val tmpList = listOf("", "", "", "", "", "", "") // api 완성 시 수정 할 것

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            //.windowInsetsPadding(WindowInsets.systemBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(319.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tmp_jeju), // 너 이미지
                    contentDescription = "여행지 샤진",
                    contentScale = ContentScale.Crop, // 비율 맞춰 채우기
                    modifier = Modifier.matchParentSize() // Box 크기에 맞춰줌!
                )

                TravelInfoTopBar(
                    iconList = listOf(R.drawable.ic_heart),
                    topBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() // 상단도 그림으로 채워지게 하기 위해서 -> 상단바 크기 자동으로 가져와줌
                )
            }

            Box( // 박스의 범위도 고민되고 이 방식이 맞나도 고민됨 일단 여기선 여행지 이름, 위치, 별점, 해시태그까지 박스
                modifier = Modifier
                    .offset(y = (-79).dp)
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .padding(top = 30.dp) //, start = (31.5).dp, end = (31.5).dp)
            ) {
                Column {
                    Column (
                        modifier = Modifier
                            .padding(horizontal = (31.5).dp)
                    ) {
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
                                    text = viewModel.selectedTravelInfo.value.avgStarRating.toString(),
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
                            hashTags = viewModel.selectedTravelInfo.value.hashTags,
                            space = 4.dp
                        )

                        Spacer(modifier = Modifier.height(57.dp))

                        Text(
                            text = viewModel.selectedTravelInfo.value.description,
                            style = Body1Regular
                        )

                        Spacer(modifier = Modifier.height(89.dp))

                        Divider(
                            color = Color(0xFFE3E3E3),
                            thickness = 1.dp
                        )

                        Spacer(modifier = Modifier.height(29.dp))


                        // 후기 리뷰작성 아이콘
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "후기",
                                style = BodyTitle
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(R.drawable.ic_review_write),
                                contentDescription = "후기 쓰기",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .clickable {
                                        Log.d("Review Write", "my click!!")
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.height(29.dp))

                        // 평균 별점 + 별점 + 리뷰개수
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = viewModel.selectedTravelInfo.value.avgStarRating.toString(),
                                style = Title
                            )

                            Spacer(modifier = Modifier.width(24.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                for (i in 5 downTo 1) { // 별점 별 개수 별로 변수 -> 배열화?
                                    val reviewCount =
                                        viewModel.selectedTravelInfo.value.starRatings[i - 1]

                                    Row {
                                        ReviewStar(
                                            starCnt = i,
                                            spaceBy = (4.83).dp,
                                            size = 10.dp
                                        )

                                        Spacer(modifier = Modifier.width(7.dp))

                                        reviewProgressBar(
                                            cnt = reviewCount,
                                            totalCnt = totalReviewCount,
                                            modifier = Modifier
                                                .width(126.dp)
                                                .height(6.dp)
                                                .border(
                                                    width = 1.dp,
                                                    color = Color(0xFFE9E9E9),
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        Text(
                                            text = "($reviewCount)",
                                            fontFamily = MainFont,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(29.dp))


                    // 더보기
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    Log.d("더보기", "my click!!")
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "더보기",
                                fontFamily = MainFont,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF3C6AFF)
                            )

                            Icon(
                                painter = painterResource(R.drawable.ic_right_arrow),
                                contentDescription = "리뷰 더보기",
                                tint = Color.Unspecified
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(29.dp))



                    // 리뷰 2개 띄우기
                    ReviewList(
                        reviewList = viewModel.selectedTravelInfo.value.reviewList,
                        maxCnt = 2
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = (31.5).dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Text(
                                text = "비슷한 여행지",
                                fontFamily = MainFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp)
                            ) {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                                ) {
                                    items(tmpList) {
                                        SimilarTravelSpots()
                                    }
                                }
                            }
                        }
                    }

                }
            }


        }
    }
}