package jjh.deliveryservice.data.remote

import jjh.deliveryservice.data.remote.response.CompanyListResponse
import jjh.devlieryservice.build_config.BuildConfig.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface DeliveryServiceApi {

  /**
   * 택배사 리스트 불러오기
   *
   * @param apiKey 스마트 택배 API Key
   * */
  @GET(Urls.COMPANY_LIST)
  suspend fun getCompanyList(
    @Query("t_key") apiKey: String = API_KEY,
  ): CompanyListResponse

  /**
   * 추천 택배사 리스트 불러오기
   *
   * @param apiKey 스마트 택배 API Key
   * @param invoiceNumber 운송장 번호
   * */
  @GET(Urls.RECOMMEND_COMPANY_LIST)
  suspend fun getRecommendCompanyList(
    @Query("t_key") apiKey: String = API_KEY,
    @Query("t_invoice") invoiceNumber: String,
  )

  /**
   * 운송장 번호 조회 택배 추적하기
   *
   * @param apiKey 스마트 택배 API Key
   * @param invoiceNumber 운송장 번호
   * @param code 택배사 코드
   * */
  @GET(Urls.TRACKING_INFO)
  suspend fun trackingInfo(
    @Query("t_key") apiKey: String = API_KEY,
    @Query("t_invoice") invoiceNumber: String,
    @Query("t_code") code: String,
  )

}