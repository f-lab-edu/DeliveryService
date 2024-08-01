package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import jjh.deliveryservice.ui.drawLine
import jjh.deliveryservice.ui.getDisplayWidth


@Composable
fun CalendarComponent(
  modifier: Modifier = Modifier,
  today: CalendarModel,
  clickedDate: CalendarModel? = null,
  dateArray: Array<CalendarModel> = arrayOf(),
  deliveryList: List<TrackingInfoModel> = listOf(),
  onDateClickListener: (List<TrackingInfoModel>) -> Unit = {},
) {
  val context = LocalContext.current
  Column(modifier = modifier.fillMaxSize()) {
    for (i in 0 until dateArray.size / 7) {
      WeekComponent(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .drawLine(context.getDisplayWidth.toFloat()),
        today = today,
        calendarModel = { dateArray[it + (i * 7)] },
        clickedDate = clickedDate,
        deliveryList = deliveryList,
        onDateClickListener = onDateClickListener,
      )
    }
  }
}

@Composable
fun WeekComponent(
  modifier: Modifier = Modifier,
  today: CalendarModel,
  deliveryList: List<TrackingInfoModel> = listOf(),
  calendarModel: (Int) -> CalendarModel,
  clickedDate: CalendarModel? = null,
  onDateClickListener: (List<TrackingInfoModel>) -> Unit = {},
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
          .weight(1f),
        today = today,
        calendarModel = model,
        clickedDate = clickedDate,
        textColor = textColor,
        deliveryList = deliveryList.filter { it.registerDate == model.toDateString() },
        onDateClickListener = onDateClickListener
      )
    }
  }
}

@Composable
fun DateComponent(
  modifier: Modifier = Modifier,
  today: CalendarModel,
  calendarModel: CalendarModel,
  clickedDate: CalendarModel? = null,
  textColor: Color,
  deliveryList: List<TrackingInfoModel> = listOf(),
  onDateClickListener: (List<TrackingInfoModel>) -> Unit = {},
) {
  val alpha = if (calendarModel.isCurrentMonth) 1f else 0.3f
  val isToday = today == calendarModel
  val isSelectedDate = clickedDate == calendarModel
  val dateTextColor = if (isToday) Color.White else textColor
  val dateBackgroundColor = when {
    isToday -> Color.Black
    isSelectedDate -> Color.LightGray
    else -> Color.Transparent
  }

  Box(
    modifier = modifier.clickable { onDateClickListener(deliveryList) }
  ) {

    Box(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .size(25.dp)
        .background(color = dateBackgroundColor, shape = CircleShape)
    ) // Background

    Text(
      modifier = Modifier
        .fillMaxWidth()
        .alpha(alpha),
      text = calendarModel.date.toString(),
      textAlign = TextAlign.Center,
      color = dateTextColor,
    ) // Date

    Row(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 30.dp)
    ) {
      repeat(deliveryList.size) {
        Dot(size = (10 - deliveryList.size).dp)
      }
    } // Row Dot
  }

}

@Preview(showBackground = true)
@Composable
private fun CalendarComponentPreview() {
  CalendarComponent(
    dateArray = CalendarUtil.getDaysInMonth(2024, 7),
    today = CalendarModel(0, 0, 0),
    clickedDate = CalendarModel(0, 0, 0),

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
    calendarModel = { calendarModel(it) },
    today = CalendarModel(0, 0, 0),
    clickedDate = CalendarModel(0, 0, 0)
  )
}

@Preview(showBackground = true)
@Composable
private fun DateCellPreview() {
  DateComponent(
    modifier = Modifier.size(50.dp),
    calendarModel = CalendarModel(2024, 6, 1),
    textColor = Color.Red,
    deliveryList = listOf(),
    onDateClickListener = {},
    today = CalendarModel(0, 0, 0),
    clickedDate = CalendarModel(0, 0, 0),
  )
}