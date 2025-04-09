package com.tlog.ui.component.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.style.BodyTitle
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun TeamNameInputField (
    text: String,
    value: String,
    onValueChange: (String) -> Unit, // 갑 변경 시 수행
    placeholderText: String, // hint
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .shadow(4.dp, shape = RoundedCornerShape(24.dp)) // 그림자 만들고
        .clip(RoundedCornerShape(24.dp)) // 크기에 맞게 짜름
        .background(Color.White) // 백그라운드가 있어야 그림자가 알맞은 위치에 보임

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = BodyTitle,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(25.dp))


    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(
            text = placeholderText,
            fontSize = 13.sp,
            fontFamily = MainFont,
            fontWeight = FontWeight.Thin
        ) },
        singleLine = singleLine,
        shape = RoundedCornerShape(20),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,     // 포커스됐을 때 아웃라인 색
            unfocusedBorderColor = Color.Transparent,  // 포커스 없을 때 아웃라인 색
            cursorColor = MainColor
        ),
        textStyle = TextStyle(
            fontFamily = MainFont,
            fontWeight = FontWeight.Thin
        ),
        trailingIcon = trailingIcon,
        modifier = defaultModifier.then(modifier)
    )
}