package com.tlog.ui.component.share

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
fun BottomLineInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "입력해주세요",
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    icon: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = MainFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                ),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                color = Color(0xFFA9A9A9),
                                fontFamily = MainFont,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    innerTextField()
                },
                modifier = modifier
                    .weight(1f)
                    .padding(start = 2.dp, top = 15.dp, bottom = 15.dp)
            )

            if (icon != null)
                icon()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MainColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}




/* -> 텍스트 필드 내부 텍스트 start 부분에 약간의 패딩이 있어서 거슬림
fun BottomLineInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "입력해주세요",
    singleLine: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = Color(0xFFA9A9A9),
                    fontFamily = MainFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        },
        singleLine = singleLine,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MainColor,
            unfocusedBorderColor = MainColor,
            cursorColor = Color.Black
        ),
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = MainFont,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}
 */