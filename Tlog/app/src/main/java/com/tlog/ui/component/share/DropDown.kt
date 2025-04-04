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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.ui.theme.MainFont


@Composable
fun DropDown(
    options: List<String>,
    value: String,
    valueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultModifier = Modifier
        .shadow(3.dp, shape = RoundedCornerShape(20.dp))
        .clip(RoundedCornerShape(20))
        .background(Color.White)


    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = defaultModifier.then(modifier)
    ) {
        Column {
            // 선택된 항목 표시 영역
            Row(
                modifier = Modifier
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

                Spacer(modifier = Modifier.width(15.dp))

                Icon(
                    painter = painterResource (if(expanded) R.drawable.ic_top_arrow else R.drawable.ic_bottom_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            // 드롭다운 펼쳐졌을 때
            if (expanded) {
                Divider(
                    color = Color(0xFFE8E8E8),
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        //.padding(horizontal = 16.dp)
                ) {
                    options.forEachIndexed { index, item ->
                        Column(
                            modifier = Modifier
                                .clickable {
                                    valueChange(item)
                                    expanded = false
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item,
                                maxLines = 1,
                                fontFamily = MainFont,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Thin,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 10.dp)
                            )


                            Divider(
                                color = Color(if (index < options.lastIndex) 0xFFE8E8E8 else 0xFFFFFFFF),
                                thickness = 0.5.dp,
                            )
                            if (index < options.lastIndex) {

                            }
                            else
                                Divider(
                                    color = Color.White,
                                    thickness = 0.5.dp,
                                )
                        }
                    }
                }
            }
        }
    }
}