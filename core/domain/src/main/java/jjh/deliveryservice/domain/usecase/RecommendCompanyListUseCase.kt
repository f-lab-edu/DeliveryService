package jjh.deliveryservice.domain.usecase

import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import javax.inject.Inject

class RecommendCompanyListUseCase @Inject constructor(
  private val companyListRepository: DeliveryServiceRepository,
) {
  suspend operator fun invoke(invoiceNumber: String): List<CompanyModel> {
    return companyListRepository.getRecommendCompanyList(invoiceNumber)
  }

}