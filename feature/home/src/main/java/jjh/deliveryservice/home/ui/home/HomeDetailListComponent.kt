package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.ui.drawLine
import jjh.deliveryservice.ui.getDisplayWidth

@Composable
fun HomeDetailListComponent(
  modifier: Modifier = Modifier,
  date: Int,
  dayOfWeek: String,
  trackingINfoModelList: List<TrackingInfoModel>,
) {
  val context = LocalContext.current

  Column(
    modifier = modifier
      .fillMaxWidth()
      .drawLine(context.getDisplayWidth.toFloat())
  ) {
    Text(
      modifier = Modifier.padding(6.dp),
      text = "$date.$dayOfWeek"
    )

    LazyColumn {
      items(
        trackingINfoModelList,
        key = { it.invoiceNo }
      ) {
        Text(text = it.name)
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
    trackingINfoModelList = listOf()
  )
}