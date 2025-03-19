package com.tlog.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tlog.ui.theme.MainColor


@Composable
fun MainInputField (
    value: String,
    onValueChange: (String) -> Unit, // 갑 변경 시 수행
    placeholder: @Composable (() -> Unit), // hint
    singleLine: Boolean = true,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        singleLine = singleLine,
        shape = RoundedCornerShape(45),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MainColor,     // 포커스됐을 때 아웃라인 색
            unfocusedBorderColor = Color(0xFFA9A9A9),  // 포커스 없을 때 아웃라인 색
            cursorColor = MainColor
        ),
        modifier = modifier
    )
}