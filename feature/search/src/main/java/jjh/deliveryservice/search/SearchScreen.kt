package jjh.deliveryservice.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.resource.emptyColors


@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  deliveryList: List<TrackingInfoModel> = listOf(),
  onBackListener: () -> Unit = {},
  onItemClickListener: (TrackingInfoModel) -> Unit = {},
  onValueChange: (String) -> Unit = {},
) {
  var isShowingSettingPopup by remember { mutableStateOf(false) }

  Column(modifier = modifier.fillMaxSize()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(54.dp)
        .drawBehind { drawLine(Color.Gray, start = Offset(0f, this.size.height), end = Offset(this.size.width, this.size.height), strokeWidth = 0.5f) },
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        modifier = Modifier
          .size(54.dp)
          .clickable { onBackListener() }
          .padding(horizontal = 16.dp)
          .fillMaxHeight(),
        imageVector = Icons.AutoMirrored.Default.ArrowBack,
        contentDescription = "StartIcon"
      )

      TextField(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        singleLine = true,
        value = "",
        onValueChange = onValueChange,
        colors = TextFieldDefaults.emptyColors(
          focusedTextColor = Color.Black,
          unfocusedTextColor = Color.Black,
          focusedPlaceholderColor = Color.Gray,
          unfocusedPlaceholderColor = Color.Gray
        ),
        placeholder = { Text("택배 이름을 입력해주세요") }
      )

      Image(
        modifier = Modifier
          .size(54.dp)
          .clickable { isShowingSettingPopup = true }
          .padding(horizontal = 16.dp)
          .fillMaxHeight(),
        imageVector = Icons.Default.Settings,
        contentDescription = "StartIcon",
      )
    } // Row

    LazyColumn {
      // TODO: 검색한 택배 리스트
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
  SearchScreen()
}