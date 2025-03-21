package com.tlog.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
        .height(52.dp)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(
                text = placeholderText,
                fontSize = 15.sp,
                fontFamily = MainFont,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = (-2).dp) // 텍스트가 살짝 아래로 치우쳐서 조정해줌
            ) },
            singleLine = singleLine,
            shape = RoundedCornerShape(45),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,     // 포커스됐을 때 아웃라인 색
                unfocusedBorderColor = Color.Transparent,  // 포커스 없을 때 아웃라인 색
                cursorColor = MainColor
            ),
            textStyle = TextStyle(
                fontFamily = MainFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            ),
            leadingIcon = leadingIcon,
            modifier = defaultModifier.then(modifier)
        )
    }
}