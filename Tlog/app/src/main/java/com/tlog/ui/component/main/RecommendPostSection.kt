package com.tlog.ui.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.tlog.R
import com.tlog.data.model.share.Post
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.Essential
import com.tlog.ui.theme.MainFont
import com.tlog.ui.theme.DefaultImage

@Composable
fun RecommendPostCard(
    post: Post,
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

@Composable
fun RecommendPostSection(
    recommendPosts: List<Post>,
    onPostClick: (String) -> Unit
) {
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
                RecommendPostCard(
                    post = post,
                    onPostClick = { onPostClick(post.id) }
                )
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
}
