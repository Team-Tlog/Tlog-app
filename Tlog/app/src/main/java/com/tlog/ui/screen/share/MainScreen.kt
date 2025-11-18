package com.tlog.ui.screen.share

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tlog.R
import com.tlog.data.model.share.LocationData
import com.tlog.ui.component.main.BannerSection
import com.tlog.ui.component.main.IssueSection
import com.tlog.ui.component.main.RecommendDestinationSection
import com.tlog.ui.component.main.RecommendPostSection
import com.tlog.ui.component.share.BottomBar
import com.tlog.ui.component.share.MainTopBar
import com.tlog.ui.navigation.Screen
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.base.BaseViewModel.UiEvent
import com.tlog.viewmodel.share.MainViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
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
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.target) {
                        if (event.clearBackStack) popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.PopBackStack -> Unit
            }
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
        viewModel.getRecommendBanner()
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
                    .height(
                        60.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    )
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
                val bannerList by viewModel.bannerList.collectAsState()

                BannerSection(
                    bannerList = bannerList,
                    onBannerClick = { title, bannerId ->
                        viewModel.navToBannerDetail(title, bannerId)
                    }
                )

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
                                            val location =
                                                viewModel.currentLocation.value ?: LocationData(
                                                    37.715133,
                                                    126.734086
                                                ) // default = 서울시청
                                            navController.navigate(
                                                Screen.Restaurant(
                                                    latitude = location.latitude.toString(),
                                                    longitude = location.longitude.toString()
                                                )
                                            )
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
                    "서울" to R.drawable.place_seoul,
                    "경기" to R.drawable.place_gyeonggi,
                    "인천" to R.drawable.place_incheon,
                    "부산" to R.drawable.place_busan,
                    "대구" to R.drawable.place_daegu,
                    "대전" to R.drawable.place_daejeon,
                    "광주" to R.drawable.place_gwangju,
                    "울산" to R.drawable.place_ulsan,
                    "충북" to R.drawable.place_chungcheongbuk,
                    "충남" to R.drawable.place_chungcheongnam,
                    "경북" to R.drawable.place_gyeongsangbuk,
                    "경남" to R.drawable.place_gyeongsangnam,
                    "전북" to R.drawable.place_jeollabuk,
                    "전남" to R.drawable.place_jeollanam,
                    "강원" to R.drawable.place_gangwon,
                    "제주" to R.drawable.place_jeju
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
                                                navController.navigate(
                                                    Screen.TravelList(
                                                        name,
                                                        name
                                                    )
                                                )
                                            }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = image),
                                            contentDescription = null,
                                            tint = Color.Unspecified,
                                            modifier = Modifier
                                                .size(size = 80.dp)
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


//                현재 사용 X
//                Spacer(modifier = Modifier.height(height = 49.dp))
//
//
//                data class Course(
//                    val title: String,
//                    val description: String,
//                    val image: Int
//                )
//
//                val popularCourseList = listOf(
//                    Course(
//                        title = "서울 청계천",
//                        description = "서울 중심에 위치한 청계천 코스",
//                        image = R.drawable.destination_img
//                    ),
//                    Course(
//                        title = "서울",
//                        description = "서울 중심에 위치한 청계천 코스",
//                        image = R.drawable.destination_img
//                    ),
//                    Course(
//                        title = "광명 여행",
//                        description = "서울 중심에 위치한 청계천 코스",
//                        image = R.drawable.destination_img
//                    ),
//                    Course(
//                        title = "대구 이월드 부근",
//                        description = "서울 중심에 위치한 청계천 코스",
//                        image = R.drawable.destination_img
//                    )
//                )
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp)
//                ) {
//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(space = 27.dp)
//                    ) {
//                        Text(
//                            text = "인기 코스",
//                            style = BodyTitle
//                        )
//
//                        LazyRow(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(height = 146.dp)
//                        ) {
//                            items(
//                                items = popularCourseList,
//                                key = { course -> course.title }
//                            ) { course ->
//                                Box(
//                                    modifier = Modifier
//                                        .padding(end = 16.dp)
//                                        .clip(shape = RoundedCornerShape(size = 10.dp))
//                                        .background(color = Color.Gray)
//                                        .clip(shape = RoundedCornerShape(size = 10.dp))
//                                        .clickable { }
//                                ) {
//                                    Image(
//                                        painter = painterResource(id = course.image),
//                                        contentDescription = null,
//                                        contentScale = ContentScale.Crop,
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                    )
//
//                                    Column(
//                                        verticalArrangement = Arrangement.spacedBy(space = 30.dp),
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(top = 24.dp, start = 24.dp)
//                                    ) {
//                                        Text(
//                                            text = course.title,
//                                            style = TextStyle(
//                                                fontFamily = MainFont,
//                                                fontSize = 24.sp,
//                                                fontWeight = FontWeight.Bold,
//                                                color = Color.White
//                                            )
//                                        )
//
//                                        Text(
//                                            text = course.description,
//                                            style = TextStyle(
//                                                fontFamily = MainFont,
//                                                fontSize = 13.sp,
//                                                fontWeight = FontWeight.Light,
//                                                color = Color.White
//                                            )
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }

                // 추천 여행지

                Spacer(modifier = Modifier.height(42.dp))

                val recommendDestinations by viewModel.recommendDestinations.collectAsState()

                RecommendDestinationSection(
                    recommendDestinations = recommendDestinations,
                    onDestinationClick = { viewModel.navToTravel(it) }
                )

                // 인기 게시글

                val recommendPosts by viewModel.recommendPosts.collectAsState()

                Spacer(modifier = Modifier.height(43.dp))

                RecommendPostSection(
                    recommendPosts = recommendPosts,
                    onPostClick = { viewModel.navToPost(it) }
                )

                // ISSUE

                Spacer(modifier = Modifier.height(43.dp))

                val localGuides by viewModel.localGuides.collectAsState()

                IssueSection(localGuides, context)
            }
        }
    }
}
