package jjh.deliveryservice.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


@Stable
fun Modifier.drawLine(width: Float) = this.then(
  drawBehind {
    drawLine(
      color = Color.LightGray,
      start = Offset(0f, 0f),
      end = Offset(width, 0f),
    )
  }
)