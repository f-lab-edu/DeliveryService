package jjh.deliveryservice.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jjh.deliveryservice.common.getString
import jjh.deliveryservice.domain.model.Level
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.resource.R
import jjh.deliveryservice.resource.CommonGreenColor
import jjh.deliveryservice.resource.emptyColors


@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  searchText: String = "",
  trackingInfoList: List<TrackingInfoModel> = listOf(),
  isEmptyResult: Boolean = false,
  onBackListener: () -> Unit = {},
  onItemClickListener: (TrackingInfoModel) -> Unit = {},
  onValueChange: (String) -> Unit = {},
) {

  // TODO: 검색내용 날짜
  val dateGroup by remember { mutableStateOf(trackingInfoList.groupBy { group -> group.registerDate }) }

  Column(modifier = modifier.fillMaxSize()) {
    SearchTitleComponent(
      searchText = searchText,
      onBackListener = onBackListener,
      onValueChange = onValueChange,
    )

    if (isEmptyResult) {
      EmptyResultComponent()
    } else {
      LazyColumn {
        items(trackingInfoList, key = { it.invoiceNo }) {
          DeliveryItem(
            modifier = Modifier
              .padding(horizontal = 16.dp)
              .padding(top = 12.dp),
            trackingInfoModel = it,
            onItemClickListener = onItemClickListener
          )
        }
      }
    }
  }
}

// 상단 검색 타이틀
@Composable
private fun SearchTitleComponent(
  searchText: String = "",
  onBackListener: () -> Unit = {},
  onValueChange: (String) -> Unit = {},
) {
  var isShowingSettingPopup by remember { mutableStateOf(false) }
  Row(
    modifier = Modifier
      .fillMaxWidth()
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
      value = searchText,
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
}

// 검색 리스트
@Composable
fun DeliveryItem(
  modifier: Modifier = Modifier,
  trackingInfoModel: TrackingInfoModel,
  onItemClickListener: (TrackingInfoModel) -> Unit = {},
) {
  Row(
    modifier
      .fillMaxWidth()
      .height(IntrinsicSize.Max)
      .background(Color(0xFFE9E9E9), shape = RoundedCornerShape(8.dp))
      .padding(vertical = 12.dp, horizontal = 12.dp)
      .clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onItemClickListener(trackingInfoModel) })
  ) {
    Box(
      modifier = Modifier
        .width(5.dp)
        .fillMaxHeight()
        .background(color = CommonGreenColor, shape = RoundedCornerShape(5.dp))
    )

    Spacer(modifier = Modifier.width(6.dp))

    Column {
      Text(
        text = trackingInfoModel.name,
        style = TextStyle(fontWeight = FontWeight.Black),
        maxLines = 1
      )
      Text(
        text = stringResource(id = trackingInfoModel.level.getString()),
        style = TextStyle(
          fontSize = 12.sp,
          fontWeight = FontWeight.Thin,
          color = Color.DarkGray
        )
      )
    }
  }
}

// 결과 없는 경우
@Composable
fun EmptyResultComponent() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .imePadding()
  ) {
    Column(
      modifier = Modifier
        .align(Alignment.Center)
    ) {
      Icon(
        modifier = Modifier
          .size(80.dp)
          .align(Alignment.CenterHorizontally)
          .padding(bottom = 10.dp),
        imageVector = Icons.Default.Search,
        contentDescription = ""
      )

      Text(text = stringResource(R.string.search_result_empty))
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {

  val trackingInfoModel = TrackingInfoModel(
    invoiceNo = "",
    name = "등록된 택배 이름",
    trackingDetails = listOf(),
    estimate = "",
    level = Level.DELIVERY_COMPLETE,
    companyCode = "companyCode"
  )

  val deliveryList = listOf(trackingInfoModel)

  SearchScreen(trackingInfoList = deliveryList)
}

@Preview(showBackground = true)
@Composable
private fun SearchTitleComponentPreview() {
  SearchTitleComponent()
}

@Preview(showBackground = true)
@Composable
private fun DeliveryItemPreview() {
  val trackingInfoModel = TrackingInfoModel(
    invoiceNo = "invoiceNo",
    name = "name",
    trackingDetails = listOf(),
    estimate = "estimate",
    level = Level.DELIVERY_COMPLETE,
    companyCode = "companyCode"
  )
  DeliveryItem(trackingInfoModel = trackingInfoModel)
}