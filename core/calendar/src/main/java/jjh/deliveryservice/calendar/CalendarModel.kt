package jjh.deliveryservice.calendar

data class CalendarModel(
  val year: Int,
  val month: Int,
  val date: Int,
  val isCurrentMonth: Boolean = false,
)
