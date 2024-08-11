package jjh.deliveryservice.search

import jjh.deliveryservice.domain.model.TrackingInfoModel

data class SearchUiState(
  val searchText: String = "",
  val searchedList: List<TrackingInfoModel> = emptyList(),
  val isEmptyResult: Boolean = false,
)