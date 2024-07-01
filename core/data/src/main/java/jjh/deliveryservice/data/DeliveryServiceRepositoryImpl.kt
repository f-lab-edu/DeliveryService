package jjh.deliveryservice.data

import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.domain.DeliveryServiceRepository
import jjh.deliveryservice.domain.model.CompanyModel


class DeliveryServiceRepositoryImpl(
  private val deliveryServiceApi: DeliveryServiceApi,
) : DeliveryServiceRepository {
  override suspend fun getCompanyList(): List<CompanyModel> {
    return deliveryServiceApi.getCompanyList().companyList.map { it.toModel() }
  }

}