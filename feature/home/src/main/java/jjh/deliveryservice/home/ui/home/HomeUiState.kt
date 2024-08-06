package jjh.deliveryservice.home.ui.home

import jjh.deliveryservice.calendar.CalendarModel
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.calendar.YearMonthDay
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.domain.model.TrackingInfoModel
import java.util.Calendar

data class HomeUiState(
  val yearMonthDay: YearMonthDay = Triple(0, 0, 0),
  private val dayOfWeekStrings: List<String> = emptyList(),
  val savedTrackingInfoList: List<TrackingInfoModel> = listOf(),
  val today: CalendarModel = Calendar.getInstance().run { CalendarModel(this.year, this.month + 1, this.date, true) },
  val clickedDate: CalendarModel? = null,
  val homeScreenDetailState: Boolean = false,
) {

  val year: Int
    get() = yearMonthDay.first

  val month: Int
    get() = yearMonthDay.second

  val date: Int
    get() = yearMonthDay.third

  fun dateArray(): Array<CalendarModel> = CalendarUtil.getDaysInMonth(year, month)

  val getDayOfWeekStrings: Array<String>
    get() = this.dayOfWeekStrings.toTypedArray()
}