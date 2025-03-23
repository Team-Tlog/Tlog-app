package com.tlog.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlog.ui.component.TopBar
import com.tlog.ui.component.TravelList
import com.tlog.ui.theme.MainColor
import com.tlog.ui.theme.MainFont
import com.tlog.viewmodel.CartViewModel


@Preview
@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(
            text = "내 장바구니"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "전체 선택",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
                .clickable {
                    viewModel.allChecked()
                    Log.d("all select", "my click!!");
                },
            fontFamily = MainFont,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = MainColor,
            textAlign = TextAlign.Right,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TravelList(
            travelList =  viewModel.travelList.value,
            setCheckBox = { index, checked ->
                viewModel.updateChecked(index, checked)
            }
        )


    }
}