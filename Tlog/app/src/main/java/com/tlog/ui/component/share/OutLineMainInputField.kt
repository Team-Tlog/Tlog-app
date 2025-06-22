package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.review.ReviewNoticeDialog
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun OutLineMainInputField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    showHelpPopup: Boolean = false,
    onDismissHelpPopup: () -> Unit = {}
) {
    val defaultModifier = Modifier
        .fillMaxWidth()
        .shadow(3.dp, shape = RoundedCornerShape(24.dp))
        .clip(RoundedCornerShape(24.dp))
        .background(Color.White)

    // 텍스트 + 아이콘 한 줄로 표시
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
        )

        // showHelpPopup 아이콘은 조건부로 표시할 수도 있음 (항상 쓰일 경우는 if 필요 없음)
        if (trailingIcon != null) {
            trailingIcon()
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 13.sp,
                fontFamily = MainFont,
                fontWeight = FontWeight.Thin
            )
        },
        singleLine = singleLine,
        shape = RoundedCornerShape(45),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MainColor
        ),
        textStyle = TextStyle(
            fontFamily = MainFont,
            fontWeight = FontWeight.Thin
        ),
        trailingIcon = null, // TextField의 trailingIcon은 X (위쪽에 넣었으니)
        modifier = defaultModifier.then(modifier)
    )

    if (showHelpPopup) {
        ReviewNoticeDialog(onDismiss = onDismissHelpPopup)
    }
}