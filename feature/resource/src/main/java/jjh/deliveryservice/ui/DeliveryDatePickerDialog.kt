package jjh.deliveryservice.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.resource.CommonGreenColor
import jjh.deliveryservice.resource.deliveryDatePickerColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryDatePickerDialog(
  modifier: Modifier = Modifier,
  onDismissRequest: () -> Unit,
  onConfirmClickListener: () -> Unit,
  state: DatePickerState = rememberDatePickerState(),
) {
  DatePickerDialog(
    modifier = modifier,
    onDismissRequest = onDismissRequest,
    confirmButton = {
      TextButton(onClick = onConfirmClickListener) {
        Text(text = "확인", style = TextStyle(color = CommonGreenColor))
      }
    },
  ) {
    Spacer(modifier = Modifier.height(20.dp))
    DatePicker(
      title = null,
      showModeToggle = false,
      state = state,
      colors = deliveryDatePickerColor()
    )
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DeliveryDatePickerDialogPreview() {
  DeliveryDatePickerDialog(
    onDismissRequest = {},
    onConfirmClickListener = {}
  )
}