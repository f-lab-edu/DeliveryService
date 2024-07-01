package jjh.deliveryservice.domain

import jjh.deliveryservice.domain.model.CompanyModel

interface DeliveryServiceRepository {

  suspend fun getCompanyList(): List<CompanyModel>
}