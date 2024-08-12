package jjh.deliveryservice.calendar

import jjh.deliveryservice.calendar.CalendarUtil.calendarStringFormat
import java.util.Calendar

data class CalendarModel(
  val year: Int,
  val month: Int,
  val date: Int,
  val isCurrentMonth: Boolean = false,
) {

  val calendar = Calendar.getInstance().apply {
    set(this.year, this.month, this.date)
  }

  fun toDateString(): String = calendarStringFormat(year, month, date)
}