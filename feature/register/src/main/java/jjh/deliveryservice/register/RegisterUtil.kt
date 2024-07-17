package jjh.deliveryservice.register

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.resource.CommonGreenColor


fun selectedColorAndStroke(isSelected: Boolean) =
  if (isSelected) CommonGreenColor to 3.dp
  else Color.Black to 1.dp