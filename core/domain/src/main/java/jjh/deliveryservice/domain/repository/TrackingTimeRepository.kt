package jjh.deliveryservice.domain.repository

import kotlinx.coroutines.flow.Flow

interface TrackingTimeRepository {
  fun saveTrackingTime(millis: Long)
  fun getLastTrackingTime(): Long
  fun saveRefreshTime(millis: Long)
  fun getRefreshTime(): Long
}