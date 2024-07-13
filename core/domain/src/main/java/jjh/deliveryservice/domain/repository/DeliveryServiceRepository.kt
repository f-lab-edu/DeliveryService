package jjh.deliveryservice.domain.repository

import jjh.deliveryservice.domain.model.CompanyModel

interface DeliveryServiceRepository {

  suspend fun getCompanyList(): List<CompanyModel>
}