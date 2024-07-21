package jjh.deliveryservice.domain.repository

import jjh.deliveryservice.data.db.dao.CompanyDao
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.data.remote.response.companys.CompanyResponse
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.CompanyModel.Companion.toEntity
import jjh.deliveryservice.domain.model.CompanyModel.Companion.toModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.model.TrackingInfoModel.Companion.toModel
import javax.inject.Inject


class DeliveryServiceRepositoryImpl @Inject constructor(
  private val deliveryServiceApi: DeliveryServiceApi,
  private val dao: CompanyDao,
) : DeliveryServiceRepository {

  /**
   * 택배사 리스트 조회
   *
   * @param needUpdate 첫 실행 여부 (택배사 리스트 업데이트)
   * */
  override suspend fun getCompanyList(needUpdate: Boolean): List<CompanyModel> {
    if (needUpdate) {
      getCompanyList()
    }

    // TODO: toModel, toEntity interface화
    return dao.getAll().map { it.toModel() }.ifEmpty { getCompanyList() }
  }

  /**
   * 추천 택배사 리스트 조회
   *
   * @param invoiceNumber 송장 번호
   * */
  override suspend fun getRecommendCompanyList(invoiceNumber: String): List<CompanyModel> {
    return deliveryServiceApi
      .getRecommendCompanyList(invoiceNumber = invoiceNumber)
      .companyList.map { it.toModel() }
  }

  /**
   * 운송장 번호 조회 택배 추적하기
   *
   * @param companyCode 택배사 코드
   * @param invoiceNumber 송장 번호
   * */
  override suspend fun trackingInfo(companyCode: String, invoiceNumber: String): TrackingInfoModel {
    return deliveryServiceApi.trackingInfo(invoiceNumber = invoiceNumber, code = companyCode).toModel()
  }

  /**
   * 택배사 리스트 조회 (API)
   * */
  private suspend fun getCompanyList(): List<CompanyModel> =
    deliveryServiceApi
      .getCompanyList()
      .companyList
      .apply { saveCompanyList(this) } // DB 저장
      .map { it.toModel() }

  /**
   * 택배사 리스트 저장 (DB)
   * */
  private suspend fun saveCompanyList(response: List<CompanyResponse>) {
    dao.insertCompanyInfo(response.map { it.toEntity() })
  }

}