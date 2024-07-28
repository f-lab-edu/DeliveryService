package jjh.deliveryservice.data.remote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.data.db.dao.DeliveryDao
import jjh.deliveryservice.data.db.datastore.COMPANY_LIST_KEY
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Companion.toEntity
import jjh.deliveryservice.data.remote.response.companys.CompanyResponse
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

  private suspend fun getSavedCompanyList(): Flow<List<CompanyModel>?> = dataStorePreferences.data
    .catch { t ->
      if (t is IOException) emit(emptyPreferences())
      else throw t
    }
    .map {
      Gson().run {
        val jsonString = it[COMPANY_LIST_KEY]
        Gson().fromJson(jsonString, object : TypeToken<List<CompanyModel>?>() {}.type)
      }
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
  private suspend fun getCompanyList(): List<CompanyResponse> = deliveryServiceApi
    .getCompanyList()
    .companyList

}