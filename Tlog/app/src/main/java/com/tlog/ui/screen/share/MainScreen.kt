package com.tlog.ui.screen.share

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.model.share.Destination
import com.tlog.data.model.share.LocationData
import com.tlog.data.model.share.Post
import com.tlog.data.model.share.RecommendDestination
import com.tlog.ui.component.main.RecommendDestinationSection
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.component.share.MainTopBar
import com.tlog.ui.component.travel.BlueHashTagGroup
import com.tlog.ui.navigation.Screen
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.Essential
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.MainViewModel



@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            viewModel.getCurrentLocation(context)
        }
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.getCurrentLocation(context)
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getRecommendPosts()
        viewModel.getRecommendDestinations()
    }

    Scaffold(
        topBar = {
            MainTopBar(
                searchIconClickable = { navController.navigate(Screen.Search) },
                notificationIconClickable = { navController.navigate(Screen.Notification) }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                BottomBar(
                    navController = navController,
                    selectedIndex = 0
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 10.dp)
            ) {


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
                    items(
                        items = recommendList,
                        key = { recommend -> recommend }
                    ) { recommend ->
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

                Spacer(modifier = Modifier.height(49.dp))


                // 아이콘 배너
                val iconList = mapOf(
                    "AI 추천 코스" to R.drawable.main_ic_ai,
                    "리뷰 쓰기" to R.drawable.main_ic_review_write,
                    "지도에서 보기" to R.drawable.main_ic_map,
                    "내 팀보기" to R.drawable.main_ic_team,
                    "스크랩" to R.drawable.scrap,
                    "식당/카페" to R.drawable.main_ic_fill_map
                )

                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                ) {
                    items(
                        items = iconList.entries.toList(),
                        key = { entry -> entry.key }
                    ) { entry ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(70.dp)
                                .clickable {
                                    when (entry.key) {
                                        "AI 추천 코스" -> {}
                                        "리뷰 쓰기" -> {
                                            navController.navigate(Screen.SearchReview)
                                        }
                                        "지도에서 보기" -> {
                                            navController.navigate(Screen.Map)
                                        }
                                        "내 팀보기" -> {
                                            navController.navigate(Screen.TeamList)
                                        }
                                        "스크랩" -> {
                                            navController.navigate(Screen.ScrapAndCart)
                                        }
                                        "식당/카페" -> {
                                            val location = viewModel.currentLocation.value ?: LocationData(37.715133, 126.734086) // default = 서울시청
                                            navController.navigate(Screen.Restaurant(latitude = location.latitude.toString(), longitude = location.longitude.toString()))
                                        }
                                    }
                                }
                            //.size(height = 62.dp, width = 70.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = entry.value),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .height((41.25).dp)
                                    .width((49.5).dp)
                                    .padding(8.dp)
                            )


                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = entry.key,
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


                Spacer(modifier = Modifier.height(38.dp))


                // 지역 별 여행지
                val cityMap = mapOf(
                    "서울" to R.drawable.login_ic_google,
                    "경기" to R.drawable.login_ic_google,
                    "인천" to R.drawable.login_ic_google,
                    "부산" to R.drawable.login_ic_google,
                    "대구" to R.drawable.login_ic_google,
                    "대전" to R.drawable.login_ic_google,
                    "광주" to R.drawable.login_ic_google,
                    "울산" to R.drawable.login_ic_google,
                    "충북" to R.drawable.login_ic_google,
                    "충남" to R.drawable.login_ic_google,
                    "경북" to R.drawable.login_ic_google,
                    "경남" to R.drawable.login_ic_google,
                    "전북" to R.drawable.login_ic_google,
                    "전남" to R.drawable.login_ic_google,
                    "강원" to R.drawable.login_ic_google,
                    "제주" to R.drawable.login_ic_google
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
                                            .clickable {
                                                navController.navigate(Screen.TravelList(name, name))
                                            }
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
                            items(
                                items = popularCourseList,
                                key = { course -> course.title }
                            ) { course ->
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





                // 추천 여행지

                Spacer(modifier = Modifier.height(42.dp))

                val recommendDestinations by viewModel.recommendDestinations.collectAsState()

                RecommendDestinationSection(recommendDestinations)




                // 인기 게시글

                val recommendPosts by viewModel.recommendPosts.collectAsState()

                Spacer(modifier = Modifier.height(43.dp))


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "인기 게시글",
                        style = BodyTitle,
                        modifier = Modifier
                            .padding(start = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        items(
                            items = recommendPosts,
                            key = { post -> post.title }
                        ) { post ->
                            RecommendPostCard(post)
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "게시글 더 보러가기",
                            style = TextStyle(
                                fontFamily = MainFont,
                                fontSize = 13.sp,
                                color = Essential,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = "right_arrow",
                            tint = Essential,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }


                // ISSUE

                Spacer(modifier = Modifier.height(43.dp))

                val localGuides by viewModel.localGuides.collectAsState()

                localGuides.let { localGuides ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "ISSUE",
                            style = BodyTitle,
                            modifier = Modifier
                                .padding(start = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        localGuides.forEach { issue ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 28.dp, vertical = 20.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, issue.infoUrl.toUri())
                                        context.startActivity(intent)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(158.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                    ) {
                                        AsyncImage(
                                            model = issue.imageUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            error = painterResource(R.drawable.tmp_jeju),
                                            modifier = Modifier
                                                .fillMaxSize()
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Text(
                                        text = issue.title,
                                        style = TextStyle(
                                            fontFamily = MainFont,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = issue.description,
                                        style = TextStyle(
                                            fontFamily = MainFont,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    BlueHashTagGroup(issue.property)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendPostCard(
    post: Post
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = post.imageUrls.firstOrNull() ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.tmp_jeju),
                modifier = Modifier
                    .height(158.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            if (post.imageUrls.size > 1) {
                AsyncImage(
                    model = post.imageUrls[1],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.tmp_jeju),
                    modifier = Modifier
                        .height(158.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = post.title,
            style = TextStyle(
                fontFamily = MainFont,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = post.description,
            style = TextStyle(
                fontFamily = MainFont,
                fontSize = 14.sp,
                color = Color.Gray
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
