package jjh.deliveryservice.home.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.home.R
import jjh.deliveryservice.resource.CommonGreenColor
import jjh.deliveryservice.ui.drawLine
import jjh.deliveryservice.ui.getDisplayWidth

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  year: Int,
  month: Int,
  today: CalendarModel,
  clickedDate: CalendarModel? = null,
  deliveryList: List<TrackingInfoModel> = listOf(),
  onDateClickListener: (year: Int, month: Int) -> Unit = { _, _ -> },
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
        onDateClickListener = onDateClickListener,
        onStartSearchScreen = onStartSearchScreen,
      ) // DateAndSearchComponent 날짜

      DayOfWeekComponent(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp),
        dayOfWeek = context.resources.getStringArray(R.array.day_of_week)
      ) // DayOfWeekComponent 요일


      val animatedValue by animateFloatAsState(
        targetValue = if (homeScreenDetailState) 1f else 0.001f,
        animationSpec = tween(durationMillis = 300),
        label = ""
      )

      CalendarComponent(
        modifier = Modifier.weight(1f),
        today = today,
        clickedDate = clickedDate,
        dateArray = CalendarUtil.getDaysInMonth(year, month),
        deliveryList = deliveryList,
        onDateClickListener = { homeScreenDetailState = !homeScreenDetailState },
      ) // CalendarComponent 달력

      Column(modifier = Modifier.weight(animatedValue)) {
        Spacer(modifier = Modifier.drawLine(context.getDisplayWidth.toFloat()))
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
  onDateClickListener: (year: Int, month: Int) -> Unit = { _, _ -> },
  onStartSearchScreen: () -> Unit = {},
) {
  Row(
    modifier = modifier
  ) {
    Row(modifier = Modifier
      .align(Alignment.CenterVertically)
      .clickable { onDateClickListener(year, month) }
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

    )
}