package jjh.deliveryservice.domain.usecase

import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeliveryTrackingInfoUseCase @Inject constructor(
  private val companyListRepository: DeliveryServiceRepository,
) {

  // 첫 실행 시 API 호출로 리스트 업데이트
  suspend operator fun invoke(
    companyCode: String,
    invoiceNumber: String,
  ): TrackingInfoModel {
    return companyListRepository.trackingInfo(companyCode = companyCode, invoiceNumber = invoiceNumber)
  }

  suspend fun isExistedDeliveryTrackingInfo(companyCode: String, invoiceNumber: String): Boolean {
    return companyListRepository.isExistedDeliveryTrackingInfo(companyCode, invoiceNumber)
  }

  suspend fun saveTrackingInfo(trackingInfoModel: TrackingInfoModel) {
    return companyListRepository.saveTrackingInfo(model = trackingInfoModel)
  }

  suspend fun getSearchByName(name: String): List<TrackingInfoModel> {
    return companyListRepository.getSearchByName(name)
  }

  suspend fun getSearchByInvoiceNumber(invoiceNumber: String): List<TrackingInfoModel> {
    return companyListRepository.getSearchByInvoiceNumber(invoiceNumber)
  }
}