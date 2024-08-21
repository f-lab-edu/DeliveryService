package jjh.deliveryservice.domain.usecase

import android.icu.util.Calendar
import jjh.deliveryservice.calendar.addField
import jjh.deliveryservice.calendar.calendar
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import jjh.deliveryservice.domain.repository.TrackingTimeRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TrackingUpdateUseCase @Inject constructor(
  private val companyListRepository: DeliveryServiceRepository,
  private val trackingTimeRepository: TrackingTimeRepository
) {

  /**
   * 배송완료되지 않은 택배 업데이트
   * */
  suspend fun update() {
    val savedTimeInMillis = trackingTimeRepository.getLastTrackingTime().first()
    val after = calendar(savedTimeInMillis).addField(Calendar.HOUR, 1).timeInMillis

    if (after < System.currentTimeMillis()) {
      companyListRepository.updateTrackingInfo()
      trackingTimeRepository.saveTrackingTime(System.currentTimeMillis())
    }
  }
}