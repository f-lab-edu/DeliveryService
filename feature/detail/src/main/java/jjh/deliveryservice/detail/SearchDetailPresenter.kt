package jjh.deliveryservice.detail

import androidx.compose.runtime.State
import jjh.deliveryservice.domain.model.TrackingInfoModel

interface SearchDetailPresenter {
  val trackingInfoModel: State<TrackingInfoModel>
  fun onBackListener()
}