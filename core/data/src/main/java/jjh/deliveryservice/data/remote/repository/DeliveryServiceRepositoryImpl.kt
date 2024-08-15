package jjh.deliveryservice.data.remote.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jjh.deliveryservice.calendar.CalendarUtil.calendarStringFormat
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.data.db.dao.DeliveryDao
import jjh.deliveryservice.data.db.datastore.COMPANY_LIST_KEY
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Companion.toEntity
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.data.remote.response.companys.CompanyResponse
import jjh.deliveryservice.data.remote.response.tracking.TrackingInfoResponse.Companion.toEntity
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Calendar
import javax.inject.Inject


class DeliveryServiceRepositoryImpl @Inject constructor(
  private val deliveryServiceApi: DeliveryServiceApi,
  private val deliveryDao: DeliveryDao,
  private val dataStorePreferences: DataStore<Preferences>,
) : DeliveryServiceRepository {

  /**
   * 택배사 리스트 조회
   *
   * @param needUpdate 첫 실행 여부 (택배사 리스트 업데이트)
   * */
  override suspend fun getCompanyList(needUpdate: Boolean): Flow<List<CompanyModel>> {
    if (needUpdate)
      return flowOf(getCompanyList()
        .map { response -> response.toModel() }
        .apply { saveCompanyList(this) }
      )

    val savedList = getSavedCompanyList()
      .map {
        it ?: getCompanyList()
          .map { response -> response.toModel() }
          .apply { saveCompanyList(this) }
      }

    return savedList
  }

  override suspend fun getCompany(companyCode: String): Flow<CompanyModel?> {
    return getSavedCompany(companyCode)
  }

  /**
   * 저장된 택배사 리스트 불러오기
   * */
  private suspend fun getSavedCompanyList(): Flow<List<CompanyModel>?> = dataStorePreferences.data
    .catch { t ->
      if (t is IOException) emit(emptyPreferences())
      else throw t
    }
    .map {
      val jsonString = it[COMPANY_LIST_KEY]
      Gson().fromJson(jsonString, object : TypeToken<List<CompanyModel>>() {}.type)
    }

  private suspend fun getSavedCompany(companyCode: String): Flow<CompanyModel?> = dataStorePreferences.data
    .catch { t ->
      if (t is IOException) emit(emptyPreferences())
      else throw t
    }
    .map {
      val jsonString = it[COMPANY_LIST_KEY]
      val list = Gson().fromJson<List<CompanyModel>>(jsonString, object : TypeToken<List<CompanyModel>>() {}.type)
      list.firstOrNull { it.companyCode == companyCode }
    }

  /**
   * DataStore 에 저장
   * */
  private suspend fun saveCompanyList(companyList: List<CompanyModel>) {
    dataStorePreferences
      .edit { preferences ->
        val jsonString: String = Gson().toJson(companyList)
        preferences[COMPANY_LIST_KEY] = jsonString
      }
  }

  /**
   * 이름으로 검색
   * */
  override suspend fun getSearchByName(
    name: String
  ): List<TrackingInfoModel> {
    return deliveryDao.getDeliveryInfoByName(name).map { it.toModel() }

  }

  /**
   * 송장번호로 검색
   * */
  override suspend fun getSearchByInvoiceNumber(
    invoiceNumber: String
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

  /**
   * 택배사 리스트 조회 (API)
   * */
  private suspend fun getCompanyList(): List<CompanyResponse> = deliveryServiceApi
    .getCompanyList()
    .companyList

}