package com.tlog.ui.component.travel.review

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.tlog.R
import com.tlog.ui.style.BodyTitle


@Composable
fun ReviewHeader(
    reviewWrite: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "후기",
            style = BodyTitle
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_review_write),
            contentDescription = "후기 쓰기",
            tint = Color.Unspecified,
            modifier = Modifier
                .clickable {
                    Log.d("ReviewHeader", "후기 쓰기 클릭")
                    reviewWrite()
                }
        )
    }
}