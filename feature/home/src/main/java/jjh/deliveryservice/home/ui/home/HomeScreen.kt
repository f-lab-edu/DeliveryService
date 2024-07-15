package jjh.deliveryservice.home.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.calendar.CalendarUtil.SATURDAY_INDEX
import jjh.deliveryservice.calendar.CalendarUtil.SUNDAY_INDEX
import jjh.deliveryservice.home.R
import jjh.deliveryservice.resource.DeliveryServiceTheme

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  onStartRegisterScreen: () -> Unit = {},
  homeViewModel: HomeViewModel = hiltViewModel(),//TODO: HomeViewModel 종속성이 생김 필요한것만
) {
  val context = LocalContext.current
  val state by homeViewModel.uiState.collectAsStateWithLifecycle()

  Box(modifier = modifier) {

    Column {

      DayOfWeekComponent(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 10.dp),
        dayOfWeek = context.resources.getStringArray(R.array.day_of_week)
      ) // 요일

      CalendarComponent(
        dateArray = CalendarUtil.getDaysInMonth(state.year, state.month)
      ) // 달력
    }


    FloatingActionButton(
      modifier = Modifier
        .padding(10.dp)
        .align(Alignment.BottomEnd),
      containerColor = Color.Gray,
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
  DeliveryServiceTheme {
    HomeScreen()
  }
}