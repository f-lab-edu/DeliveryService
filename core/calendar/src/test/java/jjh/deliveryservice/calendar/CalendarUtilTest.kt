package jjh.deliveryservice.calendar

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.Calendar


class CalendarUtilTest {
  private lateinit var calendar: Calendar

  @Before
  fun setup() {
    calendar = Calendar.getInstance()
  }

  @Test
  fun `2024년 2월 날짜 리스트 불러오기`() {

    // given
    val expected = mutableListOf<Int>().apply {
      addAll((28..31)) // 1월 마지막 주 요일들
      addAll((1..29)) // 2월
      addAll((1..2)) // 3월 첫주 요일들
    }.toTypedArray()

    // when
    val actual = CalendarUtil.getDaysInMonth(2024, 2)

    // then
    Truth.assertThat(actual).isEqualTo(expected)
  }

}