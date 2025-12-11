package com.tlog.ui.component.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.domain.model.share.RecommendPost
import com.tlog.ui.style.BodyTitle

@Composable
fun RecommendPostSection(
    recommendPosts: List<RecommendPost>,
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
                key = { post -> post.id }
            ) { post ->
                RecommendPostCard(
                    post = post,
                    onPostClick = { onPostClick(post.id) }
                )
            }
        }

//        Spacer(modifier = Modifier.height(28.dp))
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(end = 16.dp),
//            horizontalArrangement = Arrangement.End
//        ) {
//            Text(
//                text = "게시글 더 보러가기",
//                style = TextStyle(
//                    fontFamily = MainFont,
//                    fontSize = 13.sp,
//                    color = Essential,
//                    fontWeight = FontWeight.SemiBold
//                )
//            )
//
//            Icon(
//                painter = painterResource(id = R.drawable.ic_arrow_right),
//                contentDescription = "right_arrow",
//                tint = Essential,
//                modifier = Modifier
//                    .size(20.dp)
//            )
//        }
    }
}
