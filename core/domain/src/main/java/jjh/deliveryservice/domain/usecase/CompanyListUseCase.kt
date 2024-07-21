package jjh.deliveryservice.domain.usecase

import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import jjh.deliveryservice.domain.model.CompanyModel

class CompanyListUseCase(
  private val companyListRepository: DeliveryServiceRepository,
) {

  // 첫 실행 시 API 호출로 리스트 업데이트
  suspend operator fun invoke(isFirst: Boolean = false): List<CompanyModel> {
    return companyListRepository.getCompanyList(isFirst)
  }

}