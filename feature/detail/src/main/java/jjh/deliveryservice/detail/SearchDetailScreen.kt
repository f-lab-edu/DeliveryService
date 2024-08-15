package jjh.deliveryservice.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jjh.deliveryservice.common.getString
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.Level
import jjh.deliveryservice.domain.model.TrackingDetailModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.model.findLevel
import jjh.deliveryservice.resource.CommonGreenColor
import jjh.deliveryservice.resource.CommonGreenColor2
import jjh.deliveryservice.resource.R
import jjh.deliveryservice.ui.Dot
import jjh.deliveryservice.ui.Toolbar
import jjh.deliveryservice.ui.drawLine
import jjh.deliveryservice.ui.getDisplayWidth


@Composable
fun SearchDetailScreen(
  modifier: Modifier = Modifier,
  searchDetailUiState: SearchDetailUiState,
  onBackListener: () -> Unit = {},
) {
  val companyName = searchDetailUiState.companyModel?.companyName
  val nameString = if (companyName != null) {
    stringResource(R.string.delivery_company, companyName)
  } else ""

  Column(modifier = modifier) {
    Toolbar(
      title = stringResource(id = R.string.search_detail_title),
      endIcon = Icons.Default.Close,
      onEndClickListener = onBackListener,
    ) // Toolbar

    DeliveryInfoComponent(
      trackingInfoModel = searchDetailUiState.trackingInfoModel,
      companyName = nameString,
    ) // DeliveryInfoComponent

    DeliveryStatusComponent(
      level = searchDetailUiState.trackingInfoModel.level,
      trackingDetailModelList = searchDetailUiState.trackingInfoModel.getTrackingDetails()
    )

  } // Column
}

@Composable
fun DeliveryInfoComponent(
  modifier: Modifier = Modifier,
  trackingInfoModel: TrackingInfoModel,
  companyName: String,
) {
  val context = LocalContext.current

  Column(
    modifier = modifier
      .drawLine(context.getDisplayWidth.toFloat())
      .padding(horizontal = 16.dp),
  ) {
    Text(
      modifier = Modifier.padding(top = 20.dp),
      text = trackingInfoModel.name,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(20.dp))
    Text(text = stringResource(R.string.invoice_number, trackingInfoModel.invoiceNo))
    Spacer(modifier = Modifier.height(5.dp))
    Text(text = companyName)
  }
}

@Composable
fun DeliveryStatusComponent(
  modifier: Modifier = Modifier,
  level: Level,
  trackingDetailModelList: List<TrackingDetailModel>,
) {
  val context = LocalContext.current

  Column(
    modifier = modifier
      .padding(horizontal = 16.dp)
      .padding(top = 30.dp)
  ) {
    Text(
      modifier = Modifier.padding(bottom = 10.dp),
      text = stringResource(id = R.string.delivery_status),
      fontWeight = FontWeight.Bold
    ) // Text

    Box(
      modifier = Modifier
        .drawLine(context.getDisplayWidth.toFloat())
        .padding(top = 10.dp)
    )

    LazyColumn {
      itemsIndexed(
        items = trackingDetailModelList,
        key = { _, item -> "${item.level}${item.timeString}" }
      ) { index, item ->
        DeliveryStatusItem(
          modifier = Modifier.fillMaxHeight(),
          trackingDetailModel = item,
          isFirst = index == 0,
          isLast = index == trackingDetailModelList.size - 1,
          level = level,
        )
      }
    }
  }
}

@Composable
fun DeliveryStatusItem(
  modifier: Modifier = Modifier,
  trackingDetailModel: TrackingDetailModel,
  isFirst: Boolean = false,
  isLast: Boolean = false,
  level: Level = Level.UNKNOWN
) {
  val isCompleteLevel = level < findLevel(trackingDetailModel.level)
  val firstColor =
    if (isFirst) Color.Transparent
    else if (isCompleteLevel) Color.LightGray
    else CommonGreenColor

  val lastColor =
    if (isLast) Color.Transparent
    else if (isCompleteLevel) Color.LightGray
    else CommonGreenColor

  val dotColor =
    if (isCompleteLevel) Color.LightGray
    else CommonGreenColor2

  Row(
    modifier = modifier
      .fillMaxHeight()
      .height(IntrinsicSize.Max)
  ) {
    Column(
      modifier = Modifier
        .weight(0.8f),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      Box(
        modifier = Modifier
          .weight(1f)
          .width(2.dp)
          .background(color = firstColor)
      )

      Dot(
        size = 6.dp,
        color = dotColor
      )

      Box(
        modifier = Modifier
          .weight(1f)
          .width(2.dp)
          .background(color = lastColor)
      )
    }

    Column(
      modifier = Modifier
        .weight(9f)
        .align(Alignment.CenterVertically)
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = trackingDetailModel.where.ifEmpty {
          stringResource(id = findLevel(trackingDetailModel.level).getString())
        },
        fontSize = 14.sp
      )

      Text(
        modifier = Modifier.fillMaxWidth(),
        text = trackingDetailModel.timeString,
        fontSize = 12.sp
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchDetailTitleComponentPreview() {

  val trackingInfoModel = TrackingInfoModel(
    invoiceNo = "invoiceNo",
    name = "name",
    trackingDetails = listOf(),
    estimate = "estimate",
    level = Level.DELIVERY_COMPLETE,
    companyCode = "companyCode"
  )

  val companyModel = CompanyModel("companyCode", "companyName", false)

  val uiState = SearchDetailUiState(
    trackingInfoModel = trackingInfoModel,
    companyModel = companyModel
  )

  SearchDetailScreen(
    searchDetailUiState = uiState
  )
}

@Preview(showBackground = true)
@Composable
private fun DeliveryInfoComponentPreview() {

  val trackingInfoModel = TrackingInfoModel(
    invoiceNo = "invoiceNo",
    name = "name",
    trackingDetails = listOf(),
    estimate = "estimate",
    level = Level.DELIVERY_COMPLETE,
    companyCode = "companyCode"
  )

  DeliveryInfoComponent(
    modifier = Modifier,
    trackingInfoModel = trackingInfoModel,
    companyName = "companyName",
  )
}

@Preview(showBackground = true)
@Composable
private fun DeliveryStatusComponentPreview() {
  val list = listOf(
    TrackingDetailModel(
      code = "",
      kind = "집화처리",
      level = 2,
      manName = "",
      manPic = "",
      remark = "",
      telNo = "070-4404-2285",
      telNo2 = "",
      time = 1722827040000,
      timeString = "2024-08-05 12:04:00",
      where = "서울상일"
    ),
    TrackingDetailModel(
      code = "",
      kind = "간선하차",
      level = 3,
      manName = "",
      manPic = "",
      remark = "",
      telNo = "",
      telNo2 = "",
      time = 1722858428000,
      timeString = "2024-08-05 20:47:08",
      where = "곤지암Hub"
    ),
    TrackingDetailModel(
      code = "",
      kind = "간선하차",
      level = 3,
      manName = "",
      manPic = "",
      remark = "",
      telNo = "",
      telNo2 = "",
      time = 1722858626000,
      timeString = "2024-08-05 20:50:26",
      where = "곤지암Hub"
    ),
    TrackingDetailModel(
      code = "",
      kind = "간선상차",
      level = 3,
      manName = "",
      manPic = "",
      remark = "",
      telNo = "",
      telNo2 = "",
      time = 1722858759000,
      timeString = "2024-08-05 20:52:39",
      where = "곤지암Hub"
    ),
    TrackingDetailModel(
      code = "",
      kind = "간선하차",
      level = 3,
      manName = "",
      manPic = "",
      remark = "",
      telNo = "",
      telNo2 = "",
      time = 1722901455000,
      timeString = "2024-08-06 08:44:15",
      where = "관악1"
    ),
    TrackingDetailModel(
      code = "",
      kind = "간선하차",
      level = 3,
      manName = "",
      manPic = "",
      remark = "",
      telNo = "",
      telNo2 = "",
      time = 1722901620000,
      timeString = "2024-08-06 08:47:00",
      where = "관악1"
    ),
    TrackingDetailModel(
      code = "",
      kind = "배송출발 (배달예정시간:16~18시)",
      level = 5,
      manName = "정재규",
      manPic = "",
      remark = "",
      telNo = "070-7607-7486",
      telNo2 = "01047486753",
      time = 1722920680000,
      timeString = "2024-08-06 14:04:40",
      where = "서울관악미성"
    ),
    TrackingDetailModel(
      code = "",
      kind = "배송완료",
      level = 6,
      manName = "정재규",
      manPic = "",
      remark = "",
      telNo = "070-7607-7486",
      telNo2 = "01047486753",
      time = 1722930625000,
      timeString = "2024-08-06 16:50:25",
      where = "서울관악미성"
    )
  )

  val trackingDetailModelList = TrackingInfoModel.init()
    .copy(trackingDetails = list)
    .getTrackingDetails()

  DeliveryStatusComponent(
    modifier = Modifier
      .fillMaxWidth()
      .drawLine(LocalContext.current.getDisplayWidth.toFloat())
      .padding(horizontal = 16.dp),
    level = Level.DELIVERY_PROGRESS,
    trackingDetailModelList = trackingDetailModelList,
  )
}