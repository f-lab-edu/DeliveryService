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

  suspend fun update() {
    val savedTimeInMillis = trackingTimeRepository.getLastTrackingTime().first()
    val after = calendar(savedTimeInMillis).addField(Calendar.HOUR, 1).timeInMillis

    if (after < System.currentTimeMillis()) {
      companyListRepository.updateTrackingInfo()
      trackingTimeRepository.saveTrackingTime(System.currentTimeMillis())
    }
  }

  /**
   * 문자 내역중 송장번호 확인해서 업데이트 진행
   * */
  suspend fun smsUpdate(smsMessage: String) {
    companyListRepository.getNotCompleteTrackingInfo()
      .firstOrNull {
        smsMessage.contains(it.invoiceNo)
      }
      ?.apply {
        val result = companyListRepository.trackingInfo(companyCode, invoiceNo).copy(registerDate = this.registerDate)
        companyListRepository.saveTrackingInfo(result)
      }
  }
}