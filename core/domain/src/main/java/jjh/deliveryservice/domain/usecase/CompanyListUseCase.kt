package jjh.deliveryservice.domain.usecase

import jjh.deliveryservice.domain.DeliveryServiceRepository
import jjh.deliveryservice.domain.model.CompanyModel

class CompanyListUseCase(
  private val companyListRepository: DeliveryServiceRepository,
) {
  suspend operator fun invoke(): List<CompanyModel> {
    return companyListRepository.getCompanyList()
  }

}