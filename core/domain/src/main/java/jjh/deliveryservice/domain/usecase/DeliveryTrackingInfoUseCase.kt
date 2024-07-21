package jjh.deliveryservice.domain.usecase

import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository

class DeliveryTrackingInfoUseCase(
  private val companyListRepository: DeliveryServiceRepository,
) {

  // 첫 실행 시 API 호출로 리스트 업데이트
  suspend operator fun invoke(
    companyCode: String,
    invoiceNumber: String,
  ): TrackingInfoModel {
    return companyListRepository.trackingInfo(companyCode = companyCode, invoiceNumber = invoiceNumber)
  }

  // TODO: 에러 발생 시 result class or Exception viewModel에서 처리할지?


  suspend fun saveTrackingInfo(trackingInfoModel: TrackingInfoModel) {
    return companyListRepository.saveTrackingInfo(model = trackingInfoModel)
  }
}