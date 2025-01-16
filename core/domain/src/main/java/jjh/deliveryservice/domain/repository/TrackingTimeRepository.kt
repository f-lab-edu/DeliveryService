package jjh.deliveryservice.domain.repository


interface TrackingTimeRepository {
  fun saveTrackingTime(millis: Long)
  fun getLastTrackingTime(): Long
  fun saveRefreshTime(millis: Long)
  fun getRefreshTime(): Long
}