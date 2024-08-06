package jjh.deliveryservice.calendar

import java.util.Calendar

typealias YearMonthDay = Triple<Int, Int, Int>

object CalendarUtil {
  fun getCurrentDate(calendar: Calendar = Calendar.getInstance()): YearMonthDay = calendar
    .run {
      YearMonthDay(
        first = year,
        second = month + 1,
        third = date,
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
  ): Array<CalendarModel> = with(Calendar.getInstance()) {
    set(year, month - 1, 1)

    val lastWeek = getActualMaximum(Calendar.WEEK_OF_MONTH)
    val array = Array<CalendarModel?>(lastWeek * 7) { null }

    // 첫째주
    val getFirstDayOfWeekIndex = getDayOfWeek(get(Calendar.DAY_OF_WEEK))
    val last = getActualMaximum(Calendar.DAY_OF_MONTH)

    // month에 해당하는 날짜
    for (i in getFirstDayOfWeekIndex until getFirstDayOfWeekIndex + last) {
      array[i] = CalendarModel(
        year = year,
        month = month,
        date = i - getFirstDayOfWeekIndex + 1,
        isCurrentMonth = true
      )
    }


    // month - 1 달에 대한 날짜
    add(Calendar.MONTH, -1)
    val previousDayOfMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
    for (i in 0 until getFirstDayOfWeekIndex) {
      array[getFirstDayOfWeekIndex - i - 1] = CalendarModel(
        year = year,
        month = month - 1,
        date = previousDayOfMonth - i,
        isCurrentMonth = false
      )
    }

    // month + 1 달에 대한 날짜
    val nextFirstDateIndex = getFirstDayOfWeekIndex + last
    for (i in nextFirstDateIndex until array.size) {
      array[i] = CalendarModel(
        year = year,
        month = month + 1,
        date = i - nextFirstDateIndex + 1,
        isCurrentMonth = false
      )
    }

    array.mapNotNull { it }.toTypedArray()
  }

  /**
   * @param dayOfWeek 요일에 대한 int value [Calendar.get], [Calendar.DAY_OF_WEEK]
   * */
  private fun getDayOfWeek(
    dayOfWeek: Int,
  ): Int =
    when (dayOfWeek) {
      Calendar.SUNDAY -> SUNDAY_INDEX
      Calendar.MONDAY -> 1
      Calendar.TUESDAY -> 2
      Calendar.WEDNESDAY -> 3
      Calendar.THURSDAY -> 4
      Calendar.FRIDAY -> 5
      Calendar.SATURDAY -> SATURDAY_INDEX
      else -> throw IllegalArgumentException("요일이 잘못되었습니다")
    }

  /**
   * @param dayOfWeek 요일에 대한 int value [Calendar.get], [Calendar.DAY_OF_WEEK]
   * */
  internal fun getDayOfWeekString(
    dayOfWeek: Int,
  ): String =
    when (dayOfWeek) {
      Calendar.SUNDAY -> "일"
      Calendar.MONDAY -> "월"
      Calendar.TUESDAY -> "화"
      Calendar.WEDNESDAY -> "수"
      Calendar.THURSDAY -> "목"
      Calendar.FRIDAY -> "금"
      Calendar.SATURDAY -> "토"
      else -> throw IllegalArgumentException("요일이 잘못되었습니다")
    }

  const val SUNDAY_INDEX = 0
  const val SATURDAY_INDEX = 6


  private const val SUNDAY_COLOR = 0xFFFF0000
  private const val SATURDAY_COLOR = 0xFF0000FF
  private const val ELSE_COLOR = 0xFF000000

  fun getDateColor(index: Int): Long {
    return when (index) {
      SUNDAY_INDEX -> SUNDAY_COLOR
      SATURDAY_INDEX -> SATURDAY_COLOR
      else -> ELSE_COLOR
    }
  }

  fun calendarStringFormat(year: Int, month: Int, date: Int): String {
    return "$year.${String.format("%02d", month)}.${String.format("%02d", date)}"
  }
}