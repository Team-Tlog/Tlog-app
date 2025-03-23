package com.tlog.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont


@Composable
fun TravelItem(
    index: Int,
    travelName: String = "여행지 이름",
    travelDescription: String = "소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용소개내용",
    hashTags: List<String> = listOf("단풍", "가을"),
    checked: Boolean = false,
    setCheckBox: (Int, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(101.dp)
            .padding(horizontal = 20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.test_image),
            contentDescription = "$travelName 사진",
            modifier = Modifier
                .size(99.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Gray) // 추후 제거 지금 구분되는거 보려고 잠시 놔둠
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
               text = travelName,
                fontFamily = MainFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = travelDescription,
                fontFamily = MainFont,
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                modifier = Modifier.height(30.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            HashTagsGroup(hashTags) // 태그 예시임
        }

        Spacer(modifier = Modifier.width(25.dp))

        IconButton(
            onClick = {setCheckBox(index, !checked)},
            modifier = Modifier.fillMaxHeight()
        ) {
            Icon(
                painter =
                if (checked)
                    painterResource(R.drawable.ic_checkbox_checked)
                else
                    painterResource(R.drawable.ic_checkbox_unchecked),
                contentDescription = if (checked) "$travelName 체크됨" else "$travelName 체크안됨",
                tint = Color.Unspecified
            )

        }


    }
}