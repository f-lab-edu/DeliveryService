package jjh.deliveryservice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneButtonDialog(
  modifier: Modifier = Modifier,
  message: String,
  buttonText: String,
  onClick: () -> Unit,
  onDismissListener: () -> Unit
) {

  BasicAlertDialog(onDismissRequest = onDismissListener) {
    Box(
      modifier = modifier
        .background(Color.White, shape = RoundedCornerShape(10.dp))
        .defaultMinSize(minHeight = 100.dp)
        .padding(all = 16.dp)
    ) {
      Text(message)
      Spacer(modifier = Modifier.height(30.dp))
      TextButton(
        modifier = Modifier.align(Alignment.BottomEnd),
        onClick = onClick,
      ) {
        Text(text = buttonText)
      }
    }
  }

}