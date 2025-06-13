package com.tlog.ui.component.travel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.tlog.R
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.SearchTravel
import com.tlog.data.model.travel.ShopCart
import com.tlog.ui.component.share.HashTagsGroup
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.share.ScrapAndCartViewModel


@Composable
fun TravelItem(
    viewModel: ScrapAndCartViewModel = viewModel(),
    travel: ShopCart,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        if (travel.imageUrl != "NaN") {
            AsyncImage(
                model = travel.imageUrl,
                contentDescription = "${travel.name} 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(99.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        onClick(travel.id)
                    }
            )
        }
        else {
            Image( // 사진이 없을 때 이미지
                painter = painterResource(id = R.drawable.destination_img),
                contentDescription = "${travel.name} 기본 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(99.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        onClick(travel.id)
                    }
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = travel.name,
                style = Body1Bold,
                modifier = Modifier
                    .clickable {
                        onClick(travel.id)
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = travel.description ?: "설명 없음",
                fontFamily = MainFont,
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                modifier = Modifier
                    .height(30.dp)
                    .clickable {
                        onClick(travel.id)
                    }
            )

            Spacer(modifier = Modifier.height(5.dp))

            HashTagsGroup(travel.tagCountList.map { it.tagName })
        }

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

@Composable
fun ScrapTravelItem(
    viewModel: ScrapAndCartViewModel = viewModel(),
    travel: ScrapDestinationResponse,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        if (travel.imageUrl != "NaN") {
            AsyncImage(
                model = travel.imageUrl,
                contentDescription = "${travel.name} 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(99.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        onClick(travel.id)
                    }
            )
        }
        else {
            Image( // 사진이 없을 때 이미지
                painter = painterResource(id = R.drawable.destination_img),
                contentDescription = "${travel.name} 기본 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(99.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        onClick(travel.id)
                    }
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = travel.name,
                style = Body1Bold,
                modifier = Modifier
                    .clickable {
                        onClick(travel.id)
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = travel.description ?: "설명 없음",
                fontFamily = MainFont,
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                modifier = Modifier
                    .height(30.dp)
                    .clickable {
                        onClick(travel.id)
                    }
            )

            Spacer(modifier = Modifier.height(5.dp))

            HashTagsGroup(travel.tagCountList.map { it.tagName })
        }

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

@Composable
fun SearchTravelItem(
    travel: SearchTravel,
    onClick: (String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                onClick(travel.id, travel.name)
            }
    ) {
        if (travel.imageUrl == "NaN") {
            Image(
                painter = painterResource(id = R.drawable.destination_img),
                contentDescription = "${travel.name} 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(99.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
        }
        else {
            AsyncImage(
                model = travel.imageUrl,
                contentDescription = travel.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(99.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
        }

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
    }
}