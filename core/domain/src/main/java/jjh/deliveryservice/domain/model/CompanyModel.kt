package jjh.deliveryservice.domain.model

import jjh.deliveryservice.data.db.entity.CompanyEntity
import jjh.deliveryservice.data.remote.response.companys.CompanyResponse

/**
 * 택배사 정보
 *
 * @param companyCode 회사 코드
 * @param isInternational 국제 택배 여부
 * @param companyName 회사 이름
 * */
data class CompanyModel(
  val companyCode: String, // "18"
  val isInternational: Boolean, // false
  val companyName: String, // 건영택배
) {
  companion object {
    fun CompanyResponse.toModel(): CompanyModel {
      return CompanyModel(companyCode, isInternational, companyName)
    }

    fun CompanyResponse.toEntity(): CompanyEntity {
      return CompanyEntity(companyCode, isInternational, companyName)
    }

    fun CompanyEntity.toModel(): CompanyModel {
      return CompanyModel(companyCode, isInternational, companyName)
    }
  }
}