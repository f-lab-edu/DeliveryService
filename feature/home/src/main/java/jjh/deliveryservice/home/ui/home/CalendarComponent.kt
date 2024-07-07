package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.calendar.CalendarModel
import jjh.deliveryservice.calendar.CalendarUtil


@Composable
fun CalendarComponent(
  modifier: Modifier = Modifier,
  dateArray: Array<CalendarModel> = arrayOf(),
) {
  Column(modifier = modifier.fillMaxSize()) {
    for (i in 0 until dateArray.size / 7) {
      WeekComponent(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .clickable { },
        calendarModel = { dateArray[it + (i * 7)] }
      )
    }
  }
}

@Composable
fun WeekComponent(
  modifier: Modifier = Modifier,
  calendarModel: (Int) -> CalendarModel,
  clickable: (CalendarModel) -> Unit = {},
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 0 until 7) {
      val model = calendarModel(i)
      DateComponent(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .clickable { clickable(model) },
        calendarModel = model,
      )
    }
  }
}

@Composable
fun DateComponent(
  modifier: Modifier = Modifier,
  calendarModel: CalendarModel,
) {
  Text(
    modifier = modifier,
    text = calendarModel.date.toString(),
    textAlign = TextAlign.Center
  )
}

@Preview(showBackground = true)
@Composable
private fun CalendarComponentPreview() {
  CalendarComponent(
    dateArray = CalendarUtil.getDaysInMonth(2024, 7)
  )
}

@Preview(showBackground = true)
@Composable
private fun WeekComponentPreview() {
  val calendarModel: (Int) -> CalendarModel = {
    CalendarModel(2024, 6, it + 1)
  }

  WeekComponent(
    modifier = Modifier.height(50.dp),
    calendarModel = { calendarModel(it) }
  )
}

@Preview(showBackground = true)
@Composable
private fun DateCellPreview() {
  DateComponent(
    modifier = Modifier.size(50.dp),
    calendarModel = CalendarModel(2024, 6, 1),
  )
}