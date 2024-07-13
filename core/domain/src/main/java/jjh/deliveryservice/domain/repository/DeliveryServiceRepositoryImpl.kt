package jjh.deliveryservice.domain.repository

import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.CompanyModel.Companion.toModel
import javax.inject.Inject


class DeliveryServiceRepositoryImpl @Inject constructor(
  private val deliveryServiceApi: DeliveryServiceApi,
) : DeliveryServiceRepository {
  override suspend fun getCompanyList(): List<CompanyModel> {
    return deliveryServiceApi.getCompanyList().companyList.map { it.toModel() }
  }

}