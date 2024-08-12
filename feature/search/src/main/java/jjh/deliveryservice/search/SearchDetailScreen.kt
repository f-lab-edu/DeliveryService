package jjh.deliveryservice.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.ui.Toolbar


@Composable
fun SearchDetailScreen(
  modifier: Modifier = Modifier,
  trackingInfoModel: TrackingInfoModel,
  onBackListener: () -> Unit = {},
) {
  Toolbar(
    title = trackingInfoModel.name,
    startIcon = Icons.AutoMirrored.Default.ArrowBack,
    onStartClickListener = onBackListener,
  )
}


@Preview(showBackground = true)
@Composable
private fun SearchDetailTitleComponentPreview() {
  Toolbar(
    title = "택배명",
    startIcon = Icons.AutoMirrored.Default.ArrowBack,
  )
}