package com.tlog.ui.screen.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainFont


@Composable
fun MainScreen(
    //navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(horizontal = 14.dp)
            ) {

                // 상단바
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google_login_icon),
                        contentDescription = "Logo",
                        modifier = Modifier.size(38.dp),
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "검색",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = "알림",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )
                }
            }



            Spacer(modifier = Modifier.height(10.dp))

            // 추천 박스
            val recommendList = listOf(
                "RENA를 위한 9월\n추천 여행지",
                "ABCD를 위한 9월\n추천 여행지",
                "지금 핫한\n제주 여행지"
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(208.dp)
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                recommendList.forEach { recommend ->
                    item {
                        Box(
                            modifier = Modifier
                                .size(width = 312.dp, height = 188.dp)
                                .clickable {
                                    //navController.navigate("detail")
                                }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.destination_img),
                                contentDescription = recommend,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10.dp))

                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 26.dp, bottom = 20.dp, start = 29.dp)
                            ) {
                                Text(
                                    text = recommend,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )

                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }


            // 아이콘 배너
            val iconList = mapOf(
                "AI 추천 코스" to R.drawable.ai,
                "리뷰 쓰기" to R.drawable.review_write,
                "지도에서 보기" to R.drawable.looking_map,
                "내 팀보기" to R.drawable.my_team,
                "스크랩" to R.drawable.scrap,
                "지도 채우기" to R.drawable.fill_map
            )

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                iconList.forEach { name, icon ->
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(70.dp)
                                //.size(height = 62.dp, width = 70.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .height((41.25).dp)
                                    .width((49.5).dp)
                                    .padding(8.dp)
                            )

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = name,
                                style = TextStyle(
                                    fontFamily = MainFont,
                                    fontSize = 10.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(38.dp))






            // 지역 별 여행지
            val cityMap = mapOf(
                "서울" to R.drawable.google_login_icon,
                "경기" to R.drawable.google_login_icon,
                "인천" to R.drawable.google_login_icon,
                "부산" to R.drawable.google_login_icon,
                "대구" to R.drawable.google_login_icon,
                "대전" to R.drawable.google_login_icon,
                "광주" to R.drawable.google_login_icon,
                "울산" to R.drawable.google_login_icon,
                "충북" to R.drawable.google_login_icon,
                "충남" to R.drawable.google_login_icon,
                "경북" to R.drawable.google_login_icon,
                "경남" to R.drawable.google_login_icon,
                "전북" to R.drawable.google_login_icon,
                "전남" to R.drawable.google_login_icon,
                "강원" to R.drawable.google_login_icon,
                "제주" to R.drawable.google_login_icon
                // 세종 어카지?
                )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(36.dp)
            ) {
                Text(
                    text = "어떤 지역의 여행지를 찾으세요?",
                    style = TextStyle(
                        fontFamily = MainFont,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold
                    )
                )

                Spacer(modifier = Modifier.height(27.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(space = 20.dp)
                ) {
                    cityMap.entries.chunked(4).forEach { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            rowItems.forEach { (name, image) ->
                               Column(
                                   horizontalAlignment = Alignment.CenterHorizontally,
                                   verticalArrangement = Arrangement.spacedBy(space = 5.dp),
                                   modifier = Modifier
                                       .clickable {}
                               ) {
                                   Icon(
                                       painter = painterResource(id = image),
                                       contentDescription = null,
                                       tint = Color.Unspecified,
                                       modifier = Modifier
                                           .size(size = 60.dp)
                                           .clip(shape = CircleShape)
                                   )

                                   Text(
                                       text = name,
                                       style = TextStyle(
                                           fontSize = 12.sp,
                                           fontWeight = FontWeight.Medium,
                                           color = Color.Black
                                       ),
                                       textAlign = TextAlign.Center
                                   )
                               }
                            }
                        }
                    }
                }
            }









            Spacer(modifier = Modifier.height(height = 49.dp))


            data class Course(
                val title: String,
                val description: String,
                val image: Int
            )
            val popularCourseList = listOf(
                Course(
                    title = "서울 청계천",
                    description = "서울 중심에 위치한 청계천 코스",
                    image = R.drawable.destination_img
                ),
                Course(
                    title = "서울",
                    description = "서울 중심에 위치한 청계천 코스",
                    image = R.drawable.destination_img
                ),
                Course(
                    title = "광명 여행",
                    description = "서울 중심에 위치한 청계천 코스",
                    image = R.drawable.destination_img
                ),
                Course(
                    title = "대구 이월드 부근",
                    description = "서울 중심에 위치한 청계천 코스",
                    image = R.drawable.destination_img
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 27.dp)
                ) {
                    Text(
                        text = "인기 코스",
                        style = BodyTitle
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 146.dp)
                    ) {
                        popularCourseList.forEach { course ->
                            item {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .clip(shape = RoundedCornerShape(size = 10.dp))
                                        .background(color = Color.Gray)
                                        .clip(shape = RoundedCornerShape(size = 10.dp))
                                        .clickable { }
                                ) {
                                    Image(
                                        painter = painterResource(id = course.image),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    )

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(space = 30.dp),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(top = 24.dp, start = 24.dp)
                                    ) {
                                        Text(
                                            text = course.title,
                                            style = TextStyle(
                                                fontFamily = MainFont,
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        )

                                        Text(
                                            text = course.description,
                                            style = TextStyle(
                                                fontFamily = MainFont,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Light,
                                                color = Color.White
                                            )
                                        )
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