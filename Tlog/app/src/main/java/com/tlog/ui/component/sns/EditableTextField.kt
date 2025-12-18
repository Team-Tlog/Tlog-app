package com.tlog.ui.component.sns

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.ui.component.share.BottomLineInputField
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont

@Composable
fun EditableTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = MainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        )

        Spacer(modifier = Modifier.height(5.dp))

        BottomLineInputField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            singleLine = singleLine,
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (enabled) MainColor else Color(0xFFA9A9A9))
                            .clickable { onClick() }
                    ) {
                        Text(
                            text = "변경",
                            style = TextStyle(
                                fontFamily = MainFont,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .padding(horizontal = (10.5).dp, vertical = 6.dp)
                        )
                    }
                }
        )
    }
}