package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.calendar.CalendarModel
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.domain.model.Level
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.resource.CommonGreenColor
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
  onDateClickListener: (CalendarModel) -> Unit = {},
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
        deliveryList = deliveryList,
        calendarModel = { dateArray[it + (i * 7)] },
        clickedDate = clickedDate,
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
  onDateClickListener: (CalendarModel) -> Unit = {},
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
        onDateClickListener = onDateClickListener,
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
  onDateClickListener: (CalendarModel) -> Unit = {},
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

  BoxWithConstraints(
    modifier = modifier
      .padding(top = 5.dp)
      .clickable(
        indication = null, // 클릭 시 애니메이션 제거
        interactionSource = remember { MutableInteractionSource() }
      ) { onDateClickListener(calendarModel) }
  ) {

    val paddingVertical = constraints.maxHeight * 0.1f

    Text(
      modifier = Modifier
        .padding(top = 2.5.dp)
        .alpha(alpha)
        .align(Alignment.TopCenter)
        .size(25.dp)
        .background(color = dateBackgroundColor, shape = CircleShape),
      text = calendarModel.date.toString(),
      textAlign = TextAlign.Center,
      color = dateTextColor,
    ) // Date

    // Dot 목록
    Row(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = paddingVertical.dp)
    ) {
      deliveryList.forEach { trackingInfoModel ->
        val isComplete = trackingInfoModel.level == Level.DELIVERY_COMPLETE
        val color = if (isComplete) CommonGreenColor else Color.Red
        Dot(
          color = color,
          size = (10 - deliveryList.size * 2).dp
        )
        Spacer(modifier = Modifier.width(3.dp))
      }
    } // Row Dot
  }

}

@Preview(showBackground = true)
@Composable
private fun CalendarComponentPreview() {
  CalendarComponent(
    today = CalendarModel(0, 0, 0),
    clickedDate = CalendarModel(0, 0, 0),
    dateArray = CalendarUtil.getDaysInMonth(2024, 7),

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
    today = CalendarModel(0, 0, 0),
    calendarModel = { calendarModel(it) },
    clickedDate = CalendarModel(0, 0, 0)
  )
}

@Preview(showBackground = true)
@Composable
private fun DateCellPreview() {
  DateComponent(
    modifier = Modifier.size(50.dp),
    today = CalendarModel(0, 0, 0),
    calendarModel = CalendarModel(2024, 6, 1),
    clickedDate = CalendarModel(0, 0, 0),
    textColor = Color.Red,
    deliveryList = listOf(),
    onDateClickListener = {},
  )
}