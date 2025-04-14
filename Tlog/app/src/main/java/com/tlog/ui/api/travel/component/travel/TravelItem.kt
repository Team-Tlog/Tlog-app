package com.tlog.ui.api.travel.component.travel

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.R
import com.tlog.ui.api.travel.TravelData
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.api.share.CartViewModel


@Composable
fun TravelItem(
    viewModel: CartViewModel = viewModel(),
    travel: TravelData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(101.dp)
            .padding(horizontal = 20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.test_image),
            contentDescription = "${travel.name} 사진",
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
                text = travel.name,
                style = Body1Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "대충 설명 글",
                fontFamily = MainFont,
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                modifier = Modifier.height(30.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            HashTagsGroup(listOf("단풍", "가을")) // 태그 예시임
        }

        Spacer(modifier = Modifier.width(25.dp))

        IconButton(
            onClick = { viewModel.updateCheckedTravelList(travel.name) },
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.width(25.dp))
            IconButton(
                onClick = { viewModel.updateCheckedTravelList(travel.name) },
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter =
                        if (viewModel.isChecked(travel.name))
                            painterResource(R.drawable.ic_checkbox_checked)
                        else
                            painterResource(R.drawable.ic_checkbox_unchecked),
                    contentDescription = if (viewModel.isChecked(travel.name)) "${travel.name} 체크됨" else "${travel.name} 체크안됨",
                    tint = Color.Unspecified
                )

            }
        }
    }
}