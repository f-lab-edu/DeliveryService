package jjh.deliveryservice.home.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jjh.deliveryservice.calendar.CalendarModel
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.calendar.CalendarUtil.SATURDAY_INDEX
import jjh.deliveryservice.calendar.CalendarUtil.SUNDAY_INDEX
import jjh.deliveryservice.calendar.dayOfWeekString
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.home.R
import jjh.deliveryservice.resource.CommonGreenColor

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  dateArray: Array<CalendarModel>,
  year: Int,
  month: Int,
  today: CalendarModel,
  clickedDate: CalendarModel? = null,
  deliveryList: List<TrackingInfoModel> = listOf(),
  onDateChangeClickListener: (year: Int, month: Int) -> Unit = { _, _ -> },
  onDateClickListener: (CalendarModel) -> Unit = { },
  onStartSearchScreen: () -> Unit = {}, // 택배 검색하기 이동
  onStartRegisterScreen: () -> Unit = {}, // 택배 등록하기 이동
) {

  var homeScreenDetailState by remember { mutableStateOf(false) }

  val context = LocalContext.current
  Box(modifier = modifier) {
    Column {
      DateAndSearchComponent(
        modifier = Modifier.fillMaxWidth(),
        year = year,
        month = month,
        onDateChangeClickListener = onDateChangeClickListener,
        onStartSearchScreen = onStartSearchScreen,
      ) // DateAndSearchComponent 날짜

      DayOfWeekComponent(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp),
        dayOfWeek = context.resources.getStringArray(R.array.day_of_week)
      ) // DayOfWeekComponent 요일

      var dragPosition by remember { mutableStateOf(Offset(0f, 0f)) }

      // onDragEnd가 끝나기 전까지 들고있어야 하는 값, expended 여부 체크 시 homeScreenDetailState를 대신 사용
      var isDetailViewExtended by remember { mutableStateOf(false) }
      var isMoving by remember { mutableStateOf(false) }

      Column(modifier = Modifier
        .pointerInput(Unit) {
          detectVerticalDragGestures(
            onDragStart = { dragPosition = it },
            onDragEnd = {
              homeScreenDetailState = isDetailViewExtended
              isMoving = false
            },
            onVerticalDrag = { _, dragAmount ->
              if (isMoving.not()) {
                isDetailViewExtended = dragAmount < 0f
                isMoving = true
              }
            }
          )
        }
      ) {
        CalendarComponent(
          modifier = Modifier.weight(1f),
          today = today,
          clickedDate = clickedDate,
          dateArray = dateArray,
          deliveryList = deliveryList,
          onDateClickListener = {
            onDateClickListener(it)
            homeScreenDetailState = true
          },
          isDetailViewExpended = homeScreenDetailState
        ) // CalendarComponent 달력


        val animatedValue by animateFloatAsState(
          targetValue = if (homeScreenDetailState) 1f else 0.001f,
          animationSpec = tween(durationMillis = 300),
          label = ""
        )

        clickedDate?.let { model ->
          HomeDetailListComponent(
            modifier = Modifier.weight(animatedValue),
            date = model.date,
            dayOfWeek = model.calendar.dayOfWeekString,
            trackingINfoModelList = deliveryList.filter { item -> item.registerDate == model.toDateString() }
          )
        }
      }
    }


    FloatingActionButton(
      modifier = Modifier
        .padding(10.dp)
        .align(Alignment.BottomEnd),
      containerColor = CommonGreenColor,
      shape = CircleShape,
      elevation = FloatingActionButtonDefaults.elevation(0.dp),
      onClick = onStartRegisterScreen
    ) {
      Image(
        imageVector = Icons.Outlined.Add,
        colorFilter = ColorFilter.tint(Color.White),
        contentDescription = "Add Delivery",
      )
    } // Floating Button
  }

}


@Composable
fun DateAndSearchComponent(
  modifier: Modifier = Modifier,
  year: Int,
  month: Int,
  onDateChangeClickListener: (year: Int, month: Int) -> Unit = { _, _ -> },
  onStartSearchScreen: () -> Unit = {},
) {
  Row(
    modifier = modifier
  ) {
    Row(modifier = Modifier
      .align(Alignment.CenterVertically)
      .clickable { onDateChangeClickListener(year, month) }
      .padding(vertical = 10.dp)
      .padding(start = 16.dp)
    ) {
      Text(
        text = "$year.$month",
        style = TextStyle(
          color = CommonGreenColor,
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        )
      )
      Icon(
        modifier = Modifier,
        imageVector = Icons.Default.KeyboardArrowDown,
        contentDescription = "",
        tint = CommonGreenColor,
      )
    }

    Spacer(modifier = Modifier.weight(1f))

    Icon(
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .clickable { onStartSearchScreen() }
        .padding(vertical = 10.dp)
        .padding(horizontal = 16.dp),
      imageVector = Icons.Default.Search,
      tint = CommonGreenColor,
      contentDescription = ""
    )
  }
}

@Composable
fun DayOfWeekComponent(
  modifier: Modifier = Modifier,
  dayOfWeek: Array<String> = arrayOf(),
) {
  Row(modifier = modifier) {
    for ((index, s) in dayOfWeek.withIndex()) {
      val color = when (index) {
        SUNDAY_INDEX -> Color.Red
        SATURDAY_INDEX -> Color.Blue
        else -> Color.Black
      }
      Text(
        modifier = Modifier.weight(1f),
        text = s, textAlign = TextAlign.Center,
        color = color
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
  HomeScreen(
    year = 2024, month = 4,
    today = CalendarModel(0, 0, 0),
    dateArray = CalendarUtil.getDaysInMonth(2024, 4)
  )
}