package jjh.deliveryservice.detail

import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel

data class SearchDetailUiState(
  val searchText: String = "",
  val searchedList: List<TrackingInfoModel> = emptyList(),
  val trackingInfoModel: TrackingInfoModel = TrackingInfoModel.init(),
  val companyModel: CompanyModel? = null,
  val isEmptyResult: Boolean = false,
)