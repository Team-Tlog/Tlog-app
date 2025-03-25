package com.tlog.ui.screen

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.component.MainButton
import com.tlog.ui.component.TravelItem
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.CartViewModel
import com.tlog.viewmodel.TeamDetailViewModel

@Preview(showBackground = true)
@Composable
fun TeamDetailScreen(
    cartViewModel: CartViewModel = viewModel(),
    teamDetailViewModel: TeamDetailViewModel = viewModel()
) {
    val team = teamDetailViewModel.teamData
    var expanded by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val scrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val minHeaderHeight = 225.dp
    val maxHeaderHeight = if (expanded) (225 + 100 + team.members.size * 32).dp else 320.dp

    // 애니메이션 적용된 헤더 높이 계산
    val animatedHeaderHeight by animateDpAsState(
        targetValue = when {
            expanded -> maxHeaderHeight
            scrollOffset.value == 0 -> maxHeaderHeight
            else -> minHeaderHeight
        },
        label = "HeaderHeight"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(top = animatedHeaderHeight + 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartViewModel.travelList.value.size) { index ->
                val item = cartViewModel.travelList.value[index]
                TravelItem(
                    index = index,
                    travelName = item.travelData.travelName,
                    travelDescription = item.travelData.description,
                    hashTags = item.travelData.hashTags,
                    checked = item.checked,
                    setCheckBox = { i, c -> cartViewModel.updateChecked(i, c) }
                )
            }
        }

        // 🟦 파란 헤더 영역 (고정)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeaderHeight)
                .background(MainColor)
        ) {
            // 로고 (항상 고정)
            Image(
                painter = painterResource(id = R.drawable.test_image),
                contentDescription = "로고",
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.TopStart)
                    .offset(x = 14.dp, y = 44.dp)
                    //.padding(start = 14.dp, top = 44.dp)
            )

            // 팀이름 (항상 고정)
            Text(
                text = team.teamName,
                fontFamily = MainFont,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 20.dp, y = 44.dp + 32.dp + 19.dp),
                    //.padding(start = 20.dp, top = 42.dp + 32.dp +19.dp),
                fontSize = if (animatedHeaderHeight == minHeaderHeight) 18.sp else 20.sp
            )

            // 확장 가능 영역 (스크롤 위치 or 클릭 여부에 따라 변함)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 120.dp)
                    .align(Alignment.TopStart)
                    .padding(horizontal = 20.dp)
            ) {
                if (animatedHeaderHeight > minHeaderHeight) {
                    // TBTI
                    Text(
                        text = team.teamTBTI ?: "",
                        fontFamily = MainFont,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 멤버 사진 리스트
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy((-8).dp),
                        modifier = Modifier.clickable { expanded = !expanded }
                    ) {
                        team.members.forEach {
                            Image(
                                painter = painterResource(id = it.imageResId),
                                contentDescription = "팀원",
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 날짜
                    Row(
                        modifier = Modifier
                            .background(Color(0x553B3BFB), shape = CircleShape)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = team.teamStartDate ?: "",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontFamily = MainFont
                        )
                        Text(text = "~", fontSize = 12.sp, color = Color.White, fontFamily = MainFont)
                        Text(
                            text = team.teamEndDate ?: "",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontFamily = MainFont
                        )
                    }

                    if (expanded) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Column {
                            team.members.forEach {
                                Text(
                                    text = "${it.name} - ${it.tbti}",
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    fontFamily = MainFont
                                )
                            }
                        }
                    }
                }
            }
        }

        // 하단 버튼 고정
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 15.dp)
        ) {
            MainButton(
                text = "AI 코스 탐색 시작",
                onClick = {
                    Log.d("AI", "탐색 시작")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }
    }
}
