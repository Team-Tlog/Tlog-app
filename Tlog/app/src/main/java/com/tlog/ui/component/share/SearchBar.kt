package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "검색어를 입력하세요",
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp)
        //.height(52.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // 텍스트필드(플레이스홀더는 그대로, 아이콘 없음)
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = 15.sp,
                    fontFamily = MainFont,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            singleLine = singleLine,
            shape = RoundedCornerShape(45),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MainColor,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MainColor
            ),
            textStyle = TextStyle(
                fontFamily = MainFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = defaultModifier.then(modifier)
        )

        leadingIcon?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 32.dp)
            ) {
                it()
            }
        }

        //검색 바의 가장 아래 선
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .height(1.dp)
                .background(color = Color(0xFFF0F0F0))
        )
    }
}