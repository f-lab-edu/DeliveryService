package jjh.deliveryservice.calendar

import java.util.Calendar

typealias YearMonthDay = Triple<Int, Int, Int>

object CalendarUtil {
  fun getCurrentDate(): YearMonthDay = Calendar.getInstance()
    .run {
      YearMonthDay(
        first = get(Calendar.YEAR),
        second = get(Calendar.MONTH) + 1,
        third = get(Calendar.DATE)
      )
    }


  fun getDaysInMonth(year: Int, month: Int): Array<Int> = with(Calendar.getInstance()) {
    set(year, month - 1, 1)
    val lastWeek = getActualMaximum(Calendar.WEEK_OF_MONTH)
    val array = Array(lastWeek * 7) { 0 }

    // 첫째주
    val getFirstDayOfWeekIndex = getDayOfWeek(get(Calendar.DAY_OF_WEEK))
    val last = getActualMaximum(Calendar.DAY_OF_MONTH)

    // month에 해당하는 날짜
    for (i in getFirstDayOfWeekIndex until getFirstDayOfWeekIndex + last) {
      array[i] = i - getFirstDayOfWeekIndex + 1
    }


    // month - 1 달에 대한 날짜
    add(Calendar.MONTH, -1)
    val previousDayOfMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
    for (i in 0 until getFirstDayOfWeekIndex) {
      array[getFirstDayOfWeekIndex - i - 1] = previousDayOfMonth - i
    }

    // month + 1 달에 대한 날짜
    val nextFirstDateIndex = getFirstDayOfWeekIndex + last
    for (i in nextFirstDateIndex until array.size) {
      array[i] = i - nextFirstDateIndex + 1
    }

    array
  }


  private fun getDayOfWeek(dayOfWeek: Int): Int =
    when (dayOfWeek) {
      Calendar.SUNDAY -> 0
      Calendar.MONDAY -> 1
      Calendar.TUESDAY -> 2
      Calendar.WEDNESDAY -> 3
      Calendar.THURSDAY -> 4
      Calendar.FRIDAY -> 5
      Calendar.SATURDAY -> 6
      else -> throw IllegalArgumentException("요일이 잘못되었습니다")
    }
}