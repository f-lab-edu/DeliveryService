package jjh.deliveryservice.detail

import jjh.deliveryservice.domain.model.TrackingInfoModel

data class SearchDetailUiState(
  val searchText: String = "",
  val searchedList: List<TrackingInfoModel> = emptyList(),
  val isEmptyResult: Boolean = false,
)