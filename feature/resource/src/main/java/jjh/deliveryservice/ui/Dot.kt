package jjh.deliveryservice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.resource.CommonGreenColor


@Composable
fun Dot(
  modifier: Modifier = Modifier,
  size: Dp = 10.dp,
  color: Color = CommonGreenColor,
) {
  Box(
    modifier = modifier
      .size(size)
      .background(color = color, shape = CircleShape)
  )
}

@Preview(showBackground = true)
@Composable
private fun DotPreview() {
  Dot()
}