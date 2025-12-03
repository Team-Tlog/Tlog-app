package com.tlog.ui.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tlog.data.dto.share.PostDto
import com.tlog.ui.theme.DefaultImage
import com.tlog.ui.theme.MainFont

@Composable
fun RecommendPostCard(
    post: PostDto,
    onPostClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPostClick() }
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
                error = painterResource(id = DefaultImage),
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
                    error = painterResource(id = DefaultImage),
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
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(300.dp)
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
