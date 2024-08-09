package jjh.deliveryservice.domain.usecase

import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import javax.inject.Inject

class WorkManagerUseCase @Inject constructor(
  private val companyListRepository: DeliveryServiceRepository,
) {
  suspend fun doWork() {
    companyListRepository.updateTrackingInfo()
  }
}