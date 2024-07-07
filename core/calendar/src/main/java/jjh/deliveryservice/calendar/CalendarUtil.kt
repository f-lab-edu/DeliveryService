package jjh.deliveryservice.calendar

import java.util.Calendar

typealias YearMonthDay = Triple<Int, Int, Int>

object CalendarUtil {


  fun getCalendar(year: Int, month: Int, date: Int): Calendar =
    Calendar.getInstance()
      .apply {
        set(year, month - 1, date)
      }

  fun getCalendar(yearMonthDay: YearMonthDay): Calendar =
    Calendar.getInstance()
      .apply {
        set(yearMonthDay.first, yearMonthDay.second - 1, yearMonthDay.third)
      }


  fun getCurrentDate(calendar: Calendar = Calendar.getInstance()): YearMonthDay = calendar
    .run {
      YearMonthDay(
        first = get(Calendar.YEAR),
        second = get(Calendar.MONTH) + 1,
        third = get(Calendar.DATE),
      )
    }


  /**
   * 해당하는 년, 월을 입력하면 일의 배열을 준다
   *
   * @param year 월
   * @param month 일
   *
   * @return 일 배열
   * */
  fun getDaysInMonth(
    year: Int,
    month: Int,
  ): Array<Int> = with(Calendar.getInstance()) {
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

  /**
   * @param dayOfWeek 요일에 대한 int value [Calendar.get], [Calendar.DAY_OF_WEEK]
   * */
  private fun getDayOfWeek(
    dayOfWeek: Int,
  ): Int =
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