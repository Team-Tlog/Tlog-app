package com.tlog.ui.component.travel.review

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.travel.DetailReview
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont




// 시간 파싱 추후 뷰모델로 옮길 것
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ReviewItem(
    review: DetailReview
) {
    val instant = Instant.parse(review.createdAt)

    val koreaZone = ZoneId.of("Asia/Seoul")
    val koreaTime = instant.atZone(koreaZone)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val time = koreaTime.format(formatter)


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(41.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 3.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.tmp_jeju), // 유저 이미지로 변경할 것
                    contentDescription = "프로필 사진",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(100))
                        .clickable {
                            Log.d("user", "my click!!")
                        }
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = review.username,
                        style = Body1Bold,
                        modifier = Modifier
                            .clickable {
                                Log.d("user", "my click!!")
                            }
                    )

                    ReviewStar(
                        starCnt = review.rating,
                        spaceBy = 4.dp,
                        size = 12.dp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = time,
                        fontFamily = MainFont,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }

        Text(
            text = review.content,
            fontFamily = MainFont,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}