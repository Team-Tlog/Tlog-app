package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont


@Composable
fun DropDown(
    options: List<String>,
    value: String,
    valueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(fontFamily = MainFont, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = Color.Black),
    dividerColor: Color = Color(0xFFE8E8E8),
    bgColor: Color = Color.White,
    iconColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(bgColor)
            .padding(horizontal = 16.dp, vertical = (2.5).dp)
    ) {
        Column {
            // 선택된 항목 표시 영역
            Row(
                modifier = Modifier
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = textStyle,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Icon(
                    painter = painterResource (if(expanded) R.drawable.ic_arrow_top else R.drawable.ic_arrow_bottom),
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            if (expanded) {
                options.forEachIndexed { index, item ->
                    if (item != value) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    valueChange(item)
                                    expanded = false
                                }
                                .drawBehind {
                                    drawLine(
                                        color = dividerColor,
                                        start = Offset(0f, 0f),
                                        end = Offset(size.width, 0f),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                        ) {
                            Text(
                                text = item,
                                style = textStyle,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp, end = 30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}