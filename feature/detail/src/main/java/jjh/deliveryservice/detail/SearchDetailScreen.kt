package jjh.deliveryservice.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jjh.deliveryservice.domain.model.Level
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.resource.R
import jjh.deliveryservice.ui.Toolbar


@Composable
fun SearchDetailScreen(
  modifier: Modifier = Modifier,
  trackingInfoModel: TrackingInfoModel,
  onBackListener: () -> Unit = {},
) {
  Column(modifier = modifier) {
    Toolbar(
      title = trackingInfoModel.name,
      startIcon = Icons.AutoMirrored.Default.ArrowBack,
      onStartClickListener = onBackListener,
    )

    Spacer(modifier = Modifier.height(20.dp))
    Text(text = stringResource(R.string.invoice_number, trackingInfoModel.invoiceNo))
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

  SearchDetailScreen(
    trackingInfoModel = trackingInfoModel,
  )
}