package jjh.deliveryservice.calendar

import java.util.Calendar
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


val Calendar.year: Int by CalendarField(Calendar.YEAR)

val Calendar.month: Int by CalendarField(Calendar.MONTH)

val Calendar.date: Int by CalendarField(Calendar.DATE)

val Calendar.hour: Int by CalendarField(Calendar.HOUR)

val Calendar.minute: Int by CalendarField(Calendar.MINUTE)

val Calendar.second: Int by CalendarField(Calendar.SECOND)


private class CalendarField(private val field: Int) : ReadWriteProperty<Calendar, Int> {
  override fun getValue(thisRef: Calendar, property: KProperty<*>): Int = thisRef.get(field)
  override fun setValue(thisRef: Calendar, property: KProperty<*>, value: Int): Unit = thisRef.set(field, value)
}