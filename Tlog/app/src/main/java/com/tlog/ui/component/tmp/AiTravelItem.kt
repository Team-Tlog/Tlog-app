package com.tlog.ui.component.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.share.LazyHashTagsGroup
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainFont
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.tlog.R

@Composable
fun AiTravelItem(
    travelName: String,
    travelDescription: String,
    hashTags: List<String>,
    travelImageUrl: String,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = travelImageUrl,
            contentDescription = "$travelName 사진",
            modifier = Modifier
                .size(99.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.tmp_jeju)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = travelName,
                style = Body1Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = travelDescription,
                fontFamily = MainFont,
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(5.dp))

            LazyHashTagsGroup(hashTags) // 태그 예시임
        }

        IconButton(
            modifier = Modifier
                .padding(end = 24.dp),
            onClick = { onDeleteClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_x_circle),
                contentDescription = "삭제 아이콘",
                tint = Color.Unspecified
            )
        }
    }
}