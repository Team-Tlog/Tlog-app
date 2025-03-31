import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tlog.R
import com.tlog.data.model.TravelUiData
import com.tlog.ui.component.travel.TravelItem
import com.tlog.ui.theme.MainColor

@Composable
fun CityTravelList(
    city: String,
    travelItems: List<TravelUiData>,
    isLastCity: Boolean,
    cityIndex: Int,
    onDeleteClick: (TravelUiData) -> Unit,
    onUpdateChecked: (Int, Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, bottom = 4.dp, top = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_destination),
                contentDescription = "지역 아이콘",
                tint = MainColor,
                modifier = Modifier
                    .size(width = 18.dp, height = 22.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = city,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(modifier = Modifier.padding(start = 24.dp)) {
            val totalHeight = travelItems.size * 125

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(totalHeight.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                travelItems.forEachIndexed { index, item ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TravelItem(
                            index = index,
                            travelName = item.travelData.travelName,
                            travelDescription = item.travelData.description,
                            hashTags = item.travelData.hashTags,
                            checked = item.checked,
                            setCheckBox = { i, checked ->
                                onUpdateChecked(i, checked)
                            },
                            showCheckbox = false
                        )
                        IconButton(
                            onClick = { onDeleteClick(item) },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "삭제 아이콘",
                                tint = Color.Unspecified
                            )
                        }

                        if (isLastCity && index == travelItems.lastIndex)
                            Spacer(modifier = Modifier.height(145.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
