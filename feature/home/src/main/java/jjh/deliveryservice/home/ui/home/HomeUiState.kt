package jjh.deliveryservice.home.ui.home

import jjh.deliveryservice.calendar.YearMonthDay
import jjh.deliveryservice.domain.model.TrackingInfoModel

data class HomeUiState(
  val yearMonthDay: YearMonthDay = Triple(0, 0, 0),
  private val dayOfWeekStrings: List<String> = emptyList(),
  val deliveryEntities: List<TrackingInfoModel> = listOf(),
) {

  val year: Int
    get() = yearMonthDay.first

  val month: Int
    get() = yearMonthDay.second

  val date: Int
    get() = yearMonthDay.third

  val getDayOfWeekStrings: Array<String>
    get() = this.dayOfWeekStrings.toTypedArray()
}