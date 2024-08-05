package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.calendar.CalendarModel
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.ui.Dot
import jjh.deliveryservice.ui.getDisplayWidth


@Composable
fun CalendarComponent(
  modifier: Modifier = Modifier,
  dateArray: Array<CalendarModel> = arrayOf(),
  deliveryList: List<TrackingInfoModel> = listOf(),
) {
  val context = LocalContext.current
  Column(modifier = modifier.fillMaxSize()) {
    for (i in 0 until dateArray.size / 7) {
      WeekComponent(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .drawBehind {
            if (i == 0) return@drawBehind
            drawLine(
              color = Color.LightGray,
              start = Offset(0f, 0f),
              end = Offset(context.getDisplayWidth.toFloat(), 0f),
            )
          },
        calendarModel = { dateArray[it + (i * 7)] },
        deliveryList = deliveryList
      )
    }
  }
}

@Composable
fun WeekComponent(
  modifier: Modifier = Modifier,
  calendarModel: (Int) -> CalendarModel,
  onDateClickListener: (CalendarModel) -> Unit = {},
  deliveryList: List<TrackingInfoModel> = listOf(),
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 0 until 7) {
      val model = calendarModel(i)
      val textColor = Color(CalendarUtil.getDateColor(i))

      DateComponent(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .clickable { onDateClickListener(model) },
        calendarModel = model,
        textColor = textColor,
        deliveryList.filter { it.registerDate == model.toDateString() }
      )
    }
  }
}

@Composable
fun DateComponent(
  modifier: Modifier = Modifier,
  calendarModel: CalendarModel,
  textColor: Color,
  deliveryList: List<TrackingInfoModel> = listOf(),
) {
  val alpha = if (calendarModel.isCurrentMonth) 1f else 0.3f

  Box(modifier = modifier) {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .alpha(alpha),
      text = calendarModel.date.toString(),
      textAlign = TextAlign.Center,
      color = textColor
    )

    Row(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 30.dp)
    ) {
      repeat(deliveryList.size) {
        Dot(size = (10 - deliveryList.size).dp)
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
    Color.Red,
    deliveryList = listOf()
  )
}