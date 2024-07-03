package jjh.deliveryservice.domain.model

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
)