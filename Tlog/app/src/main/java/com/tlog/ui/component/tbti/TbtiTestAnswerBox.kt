package com.tlog.ui.component.tbti

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.beginning.TbtiTestViewModel


@Composable
fun TbtiTestAnswerBox(
    selectIdx: Int,
    answer: String,
    viewModel: TbtiTestViewModel = viewModel()
) {
    var color by remember { mutableStateOf(Color.White) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .clickable {
                Log.d("choose", "my click!!")
                color = if (color == Color.White) MainColor else Color.White
                viewModel.addList(selectIdx)
            }
            .height(58.dp)
    ) {
        Text(
            text = answer,
            fontFamily = MainFont,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = if (color == Color.White) Color.Black else Color.White,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}