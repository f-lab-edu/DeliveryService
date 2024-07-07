package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jjh.deliveryservice.calendar.CalendarModel
import jjh.deliveryservice.calendar.CalendarUtil


@Composable
fun CalendarComponent(
  modifier: Modifier = Modifier,
  dateArray: Array<CalendarModel> = arrayOf(),
) {
  Row(modifier = modifier) {
    for (i in 0 until 7) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        for (j in 0 until dateArray.size / 7) {
          Text(
            modifier = Modifier
              .weight(1f),
            text = dateArray[i + (j * 7)].date.toString(),
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CalendarComponentPreview() {
  CalendarComponent(
    dateArray = CalendarUtil.getDaysInMonth(2024, 7)
  )
}
