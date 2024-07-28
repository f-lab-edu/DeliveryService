package jjh.deliveryservice.domain.repository

import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import kotlinx.coroutines.flow.Flow

interface DeliveryServiceRepository {

  suspend fun getCompanyList(needUpdate: Boolean): Flow<List<CompanyModel>>

  suspend fun getRecommendCompanyList(invoiceNumber: String): List<CompanyModel>

  @Throws(IllegalArgumentException::class)
  suspend fun trackingInfo(
    companyCode: String,
    invoiceNumber: String,
  ): TrackingInfoModel

  suspend fun saveTrackingInfo(model: TrackingInfoModel)
}