package jjh.deliveryservice.domain.usecase

import jjh.deliveryservice.domain.repository.TrackingTimeRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TrackingTimeUseCase @Inject constructor(
  private val trackingTimeRepository: TrackingTimeRepository
) {
  suspend fun saveTrackingTime(millis: Long) {
    trackingTimeRepository.saveTrackingTime(millis)
  }

  suspend fun getLastTrackingTime(): Long {
    return trackingTimeRepository.getLastTrackingTime().first()
  }

}