package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jjh.deliveryservice.domain.model.Level
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.resource.CommonGreenColor
import jjh.deliveryservice.ui.drawLine
import jjh.deliveryservice.ui.getDisplayWidth

@Composable
fun HomeDetailListComponent(
  modifier: Modifier = Modifier,
  date: Int,
  dayOfWeek: String,
  trackingInfoModelList: List<TrackingInfoModel>,
  onItemClickListener: (TrackingInfoModel) -> Unit = {}
) {
  val context = LocalContext.current

  Column(
    modifier = modifier
      .fillMaxWidth()
      .drawLine(context.getDisplayWidth.toFloat())
      .padding(vertical = 6.dp)
      .padding(horizontal = 20.dp)
  ) {
    Text(text = "$date.$dayOfWeek")

    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
      items(
        trackingInfoModelList,
        key = { it.invoiceNo }
      ) {
        val isComplete = it.level == Level.DELIVERY_COMPLETE
        val color = if (isComplete) CommonGreenColor else Color.Red

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(vertical = 10.dp)
            .clickable { onItemClickListener(it) }
        ) {
          Box(
            modifier = Modifier
              .background(color = color, shape = RoundedCornerShape(5.dp))
              .width(5.dp)
              .height(38.dp)
          )

          Column(
            modifier = Modifier
              .padding(start = 5.dp)
              .align(Alignment.CenterVertically)
          ) {
            Text(
              text = it.name,
              style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 13.sp
              )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "도착 예정 시간: " + it.estimate,
              style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                color = Color.Gray
              )
            )
          }
        }
      }
    }

  }
}

@Preview(showBackground = true)
@Composable
private fun HomeDetailListComponentPreview() {
  HomeDetailListComponent(
    modifier = Modifier,
    date = 20,
    dayOfWeek = "월",
    trackingInfoModelList = listOf()
  )
}