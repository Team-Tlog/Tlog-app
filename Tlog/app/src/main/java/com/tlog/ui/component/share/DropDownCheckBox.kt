package com.tlog.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont


@Composable
fun DropDownCheckBox(
    city: String,
    options: List<String>,
    value: String,
    checkedSet: Set<String>,
    onClick: (String) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(Color.White)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = MainColor,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column {
            // 선택된 항목 표시 영역
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 16.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    fontFamily = MainFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Thin
                )
            }

            // 드롭다운 펼쳐졌을 때
            if (expanded) {
                HorizontalDivider(
                    color = Color(0xFFE8E8E8),
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    options.forEachIndexed { index, item ->
                        val checked = city + item in checkedSet

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onClick(city + item)
                                    //expanded = false
                                }
                        ) {
                            Row {
                                Text(
                                    text = item,
                                    fontFamily = MainFont,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Thin,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp, vertical = 10.dp)
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(if (checked) R.drawable.ic_filled_checkbox_checked else R.drawable.ic_filled_checkbox_unchecked),
                                        contentDescription = "$item" + if (checked) "체크해제하기" else "체크하기",
                                        tint = Color.Unspecified,
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                    )
                                }
                            }
                            if (index < options.lastIndex) {
                                HorizontalDivider(
                                    color = Color(0xFFE8E8E8),
                                    thickness = 0.5.dp,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}