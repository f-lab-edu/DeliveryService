package jjh.deliveryservice.data.remote

import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.data.db.dao.CompanyDao
import jjh.deliveryservice.data.db.dao.DeliveryDao
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Companion.toEntity
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import java.util.Calendar
import javax.inject.Inject


class DeliveryServiceRepositoryImpl @Inject constructor(
  private val deliveryServiceApi: DeliveryServiceApi,
  private val companyDao: CompanyDao,
  private val deliveryDao: DeliveryDao,
) : DeliveryServiceRepository {

  /**
   * 택배사 리스트 조회
   *
   * @param needUpdate 첫 실행 여부 (택배사 리스트 업데이트)
   * */
  override suspend fun getCompanyList(needUpdate: Boolean): List<CompanyModel> {
    return if (needUpdate)
      getCompanyList()
    else
      companyDao.getAll().map { it.toModel() }.ifEmpty { getCompanyList() }
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
  @Throws(IllegalArgumentException::class)
  override suspend fun trackingInfo(companyCode: String, invoiceNumber: String): TrackingInfoModel {
    if (deliveryDao.getAllDeliveryInfo().isEmpty()) {
      return deliveryServiceApi.trackingInfo(invoiceNumber = invoiceNumber, code = companyCode).toModel()
    }

    throw IllegalArgumentException("이미 등록된 택배입니다")
  }

  override suspend fun saveTrackingInfo(model: TrackingInfoModel) {
    val entity = model
      .toEntity()
      .copy(
        registerDate = Calendar.getInstance().run { "$year.${month + 1}.$date" }
      )

    return deliveryDao.insertTrackingInfo(listOf(entity))
  }

  /**
   * 택배사 리스트 조회 (API)
   * */
  private suspend fun getCompanyList(): List<CompanyModel> =
    deliveryServiceApi
      .getCompanyList()
      .companyList
      .map { it.toModel() }
}