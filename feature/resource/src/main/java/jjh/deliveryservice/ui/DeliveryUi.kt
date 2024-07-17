package jjh.deliveryservice.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.resource.CommonGreenColor


@Composable
fun DeliveryOutlineTextField(
  modifier: Modifier = Modifier,
  text: String,
  textChangeListener: (String) -> Unit,
  keyboardType: KeyboardType,
  isError: Boolean,
  placeholder: @Composable () -> Unit,
) {
  OutlinedTextField(
    modifier = modifier,
    shape = RoundedCornerShape(10.dp),
    placeholder = placeholder,
    colors = TextFieldDefaults.colors(
      disabledContainerColor = Color.Transparent,
      focusedContainerColor = Color.Transparent,
      unfocusedContainerColor = Color.Transparent
    ),
    textStyle = TextStyle(color = CommonGreenColor),
    isError = isError,
    singleLine = true,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    prefix = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
    value = text,
    onValueChange = textChangeListener
  )
}

@Composable
fun Toolbar(
  title: String,
  @DrawableRes startIcon: Int? = null,
  onStartClickListener: () -> Unit = {},
  @DrawableRes endIcon: Int? = null,
  onEndClickListener: () -> Unit = {},
) {


  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(54.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (startIcon != null)
      Image(
        modifier = Modifier
          .size(54.dp)
          .clickable { onStartClickListener() }
          .padding(horizontal = 16.dp)
          .fillMaxHeight(),
        painter = painterResource(id = startIcon),
        contentDescription = "StartIcon"
      )
    else
      Spacer(modifier = Modifier.size(54.dp))

    Text(
      modifier = Modifier
        .weight(1f),
      text = title,
      textAlign = TextAlign.Center
    )

    if (endIcon != null)
      Image(
        modifier = Modifier
          .size(54.dp)
          .clickable { onStartClickListener() }
          .padding(horizontal = 16.dp)
          .fillMaxHeight(),
        painter = painterResource(id = endIcon),
        contentDescription = "StartIcon",
      )
    else
      Spacer(modifier = Modifier.size(54.dp))
  }
}

@Composable
fun Toolbar(
  title: String,
  startIcon: ImageVector? = null,
  onStartClickListener: () -> Unit = {},
  endIcon: ImageVector? = null,
  onEndClickListener: () -> Unit = {},
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(54.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (startIcon != null)
      Image(
        modifier = Modifier
          .size(54.dp)
          .clickable { onStartClickListener() }
          .padding(horizontal = 16.dp)
          .fillMaxHeight(),
        imageVector = startIcon,
        contentDescription = "StartIcon"
      )
    else
      Spacer(modifier = Modifier.size(54.dp))


    Text(
      modifier = Modifier
        .weight(1f),
      text = title,
      textAlign = TextAlign.Center
    )

    if (endIcon != null)
      Image(
        modifier = Modifier
          .size(54.dp)
          .clickable { onEndClickListener() }
          .padding(horizontal = 16.dp)
          .fillMaxHeight(),
        imageVector = endIcon,
        contentDescription = "StartIcon",
      )
    else
      Spacer(modifier = Modifier.size(54.dp))

  }
}

@Preview(showBackground = true)
@Composable
private fun DeliveryOutlineTextFieldPreview() {
  DeliveryOutlineTextField(
    text = "text",
    textChangeListener = {},
    keyboardType = KeyboardType.Text,
    isError = false,
    placeholder = {})
}

@Preview(showBackground = true)
@Composable
private fun ToolbarPreview() {

  var text by remember {
    mutableStateOf("이름쓰")
  }
  Toolbar(
    startIcon = null,
    onStartClickListener = { text = "바껴라라라 얍!" },
    endIcon = Icons.Default.ArrowBack,
    onEndClickListener = { text = "바껴라라라 얍!" },
    title = text
  )
}