package jjh.deliveryservice.calendar

import jjh.deliveryservice.calendar.CalendarUtil.getDayOfWeekString
import java.util.Calendar
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


val Calendar.year: Int by CalendarField(Calendar.YEAR)

val Calendar.month: Int by CalendarField(Calendar.MONTH)

val Calendar.date: Int by CalendarField(Calendar.DATE)

val Calendar.hour: Int by CalendarField(Calendar.HOUR)

val Calendar.minute: Int by CalendarField(Calendar.MINUTE)

val Calendar.second: Int by CalendarField(Calendar.SECOND)

val Calendar.dayOfWeek: Int by CalendarField(Calendar.DAY_OF_WEEK)

val Calendar.dayOfWeekString: String
  get() = getDayOfWeekString(dayOfWeek)

val Calendar.monthLastDate: Int
  get() = getActualMaximum(Calendar.DAY_OF_MONTH)

private class CalendarField(private val field: Int) : ReadWriteProperty<Calendar, Int> {
  override fun getValue(thisRef: Calendar, property: KProperty<*>): Int = thisRef.get(field)
  override fun setValue(thisRef: Calendar, property: KProperty<*>, value: Int): Unit = thisRef.set(field, value)
}