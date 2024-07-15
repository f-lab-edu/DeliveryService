package jjh.deliveryservice.register

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


fun selectedColorAndStroke(isSelected: Boolean) =
  if (isSelected) Color(0xFF075500) to 3.dp
  else Color.Black to 1.dp