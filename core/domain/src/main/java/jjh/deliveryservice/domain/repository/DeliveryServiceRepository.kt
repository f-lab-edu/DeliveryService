package jjh.deliveryservice.domain.repository

import jjh.deliveryservice.data.remote.response.tracking.TrackingInfoResponse
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel

interface DeliveryServiceRepository {

  suspend fun getCompanyList(needUpdate: Boolean): List<CompanyModel>

  suspend fun getRecommendCompanyList(invoiceNumber: String): List<CompanyModel>

  suspend fun trackingInfo(
    companyCode: String,
    invoiceNumber: String,
  ): TrackingInfoModel
}