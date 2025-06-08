package com.tlog.ui.component.tbti

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.beginning.TbtiTestViewModel

@Composable
fun TbtiTestAnswerBox(
    selectIdx: Int,
    answer: String,
    selected: Boolean,
    onClick: () -> Unit,
    viewModel: TbtiTestViewModel = viewModel()
) {
    val defaultBg = Color(0xFFF6F6F8)
    val selectedBg = Color(0xFFF2F6FF)
    val borderColor = Color(0xFF87A9FE)

    Box(
        modifier = Modifier
            .width(313.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) selectedBg else defaultBg)
            .then(
                if (selected)
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                else Modifier
            )
            .clickable {
                onClick()
                // Example interaction with ViewModel if needed
                viewModel.onAnswerSelected(selectIdx)
            }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = answer,
                fontFamily = MainFont,
                fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = if (selected) Color(0xFF000000) else Color(0xFF7E7E7E),
                lineHeight = 18.sp,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}