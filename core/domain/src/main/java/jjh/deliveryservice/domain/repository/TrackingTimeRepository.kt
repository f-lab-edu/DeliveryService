package jjh.deliveryservice.domain.repository

import kotlinx.coroutines.flow.Flow

interface TrackingTimeRepository {
  suspend fun saveTrackingTime(millis: Long)
  suspend fun getLastTrackingTime(): Flow<Long>
}