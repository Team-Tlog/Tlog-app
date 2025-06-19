package com.tlog.ui.component.sns

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import com.tlog.R
import com.tlog.ui.theme.Essential
import com.tlog.ui.theme.MainFont

@Composable
fun ViewCourseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    buttonText: String = "코스 보러가기"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buttonText,
                fontFamily = MainFont,
                fontWeight = FontWeight.SemiBold,
                color = Essential
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = buttonText,
                modifier = Modifier.size(16.dp),
                tint = Color.Unspecified
            )
        }
    }
}