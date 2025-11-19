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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tlog.R
import com.tlog.data.model.travel.AiTravel
import com.tlog.ui.component.tmp.AiTravelItem
import com.tlog.ui.style.Body1Bold
import com.tlog.ui.theme.MainColor

@Composable
fun CityTravelList(
    city: String,
    travelItems: List<AiTravel>,
    onDeleteClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, bottom = 4.dp, top = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_destination),
                contentDescription = "화살표 아이콘",
                tint = MainColor,
                modifier = Modifier
                    .size(width = 18.dp, height = 22.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = city,
                style = Body1Bold
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
                    AiTravelItem(
                        travelName = item.name,
                        travelDescription = item.description,
                        hashTags = item.tagCountList?.map { it.tagName } ?: emptyList(),
                        travelImageUrl = item.imageUrl ?: "",
                        onDeleteClick = { onDeleteClick(item.name) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
                Spacer(modifier = Modifier.height(145.dp))
            }
        }
    }
}
