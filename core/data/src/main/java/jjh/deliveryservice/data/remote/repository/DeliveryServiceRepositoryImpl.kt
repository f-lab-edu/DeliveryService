package jjh.deliveryservice.data.remote.repository

import jjh.deliveryservice.calendar.CalendarUtil.calendarStringFormat
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.common.fromJson
import jjh.deliveryservice.common.toJson
import jjh.deliveryservice.data.db.dao.DeliveryDao
import jjh.deliveryservice.data.db.datastore.COMPANY_LIST_KEY
import jjh.deliveryservice.data.db.datastore.DeliveryDataStore
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Companion.toEntity
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.data.remote.response.companys.CompanyResponse
import jjh.deliveryservice.data.remote.response.tracking.TrackingInfoResponse.Companion.toEntity
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import java.util.Calendar
import javax.inject.Inject


class DeliveryServiceRepositoryImpl @Inject constructor(
  private val deliveryServiceApi: DeliveryServiceApi,
  private val deliveryDao: DeliveryDao,
  dataStore: DeliveryDataStore,
) : DeliveryServiceRepository, DeliveryDataStore by dataStore {

  private var prefCompanyList: String by stringDataStore(COMPANY_LIST_KEY)

  /**
   * 택배사 리스트 조회
   *
   * @param needUpdate 첫 실행 여부 (택배사 리스트 업데이트)
   * */
  override suspend fun getCompanyList(needUpdate: Boolean): List<CompanyModel> {
    if (needUpdate)
      return getAndSaveCompanyList()

    val savedList = getSavedCompanyList().ifEmpty { getAndSaveCompanyList() }
    return savedList
  }

  override suspend fun getCompany(companyCode: String): CompanyModel? = prefCompanyList
    .fromJson<List<CompanyModel>>()
    ?.firstOrNull { it.companyCode == companyCode }

  /**
   * 저장된 택배사 리스트 불러오기
   * */
  private fun getSavedCompanyList(): List<CompanyModel> = prefCompanyList.fromJson() ?: listOf()

  /**
   * DataStore 에 저장
   * */
  private fun saveCompanyList(companyList: List<CompanyModel>) {
    prefCompanyList = companyList.toJson()
  }

  /**
   * 이름으로 검색
   * */
  override suspend fun getSearchByName(
    name: String,
  ): List<TrackingInfoModel> {
    return deliveryDao.getDeliveryInfoByName(name).map { it.toModel() }

  }

  /**
   * 송장번호로 검색
   * */
  override suspend fun getSearchByInvoiceNumber(
    invoiceNumber: String,
  ): List<TrackingInfoModel> {
    return deliveryDao.getDeliveryInfoByInvoiceNo(invoiceNumber).map { it.toModel() }
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
    return deliveryServiceApi.trackingInfo(invoiceNumber = invoiceNumber, code = companyCode).toModel(companyCode)
  }

  /**
   * 택배 저장하기
   *
   * @param model 택배 정보
   * */
  override suspend fun saveTrackingInfo(model: TrackingInfoModel) {
    val entity = model.toEntity()
      .copy(registerDate = Calendar.getInstance().run { calendarStringFormat(this.year, this.month + 1, this.date) })

    return deliveryDao.insertTrackingInfo(listOf(entity))
  }


  /**
   * 저장된 택배 확인
   *
   * @param companyCode 택배사 코드
   * @param invoiceNumber 송장 번호
   * */
  override suspend fun isExistedDeliveryTrackingInfo(companyCode: String, invoiceNumber: String): Boolean {
    return deliveryDao.getDeliveryInfo(invoiceNumber) != null
  }

  /**
   * 기간 내 택배 확인 (보통 월별)
   *
   * @param startDate 검색 시작 날짜
   * @param endDate 검색 종료 날짜
   * */
  override suspend fun getDateDeliveryInfo(startDate: String, endDate: String): List<TrackingInfoModel> {
    return deliveryDao.getDateDeliveryInfo(startDate, endDate).map { it.toModel() }
  }

  override suspend fun updateTrackingInfo() {
    val resultList = deliveryDao.getNotCompletedDeliveryInfo()
      .map {
        deliveryServiceApi.trackingInfo(
          invoiceNumber = it.invoiceNo,
          code = it.companyCode
        )
          .toEntity(companyCode = it.companyCode)
          .copy(registerDate = it.registerDate)
      }
    deliveryDao.insertTrackingInfo(resultList)
  }


  override suspend fun getNotCompleteTrackingInfo(): List<TrackingInfoModel> {
    return deliveryDao.getNotCompletedDeliveryInfo().map { it.toModel() }
  }

  /**
   * 택배사 리스트 조회 (API)
   * */
  private suspend fun getCompanyList(): List<CompanyResponse> = deliveryServiceApi
    .getCompanyList()
    .companyList

  private suspend fun getAndSaveCompanyList() = getCompanyList()
    .map { response -> response.toModel() }
    .apply { saveCompanyList(this) }
}