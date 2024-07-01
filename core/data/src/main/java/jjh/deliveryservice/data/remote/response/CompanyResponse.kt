package jjh.deliveryservice.data.remote.response

import com.google.gson.annotations.SerializedName
import jjh.deliveryservice.domain.model.CompanyModel

/**
 * [jjh.deliveryservice.data.remote.DeliveryServiceApi.getCompanyList] Response
 *
 * @param companyCode 회사 코드
 * @param isInternational 국제 택배 여부
 * @param companyName 회사 이름
 * */
data class CompanyResponse(

  @SerializedName("Code")
  val companyCode: String, // "18"

  @SerializedName("International")
  val isInternational: Boolean, // false

  @SerializedName("Name")
  val companyName: String, // 건영택배
) {
  fun toModel(): CompanyModel {
    return CompanyModel(companyCode, isInternational, companyName)
  }
}