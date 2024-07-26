package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import jjh.deliveryservice.domain.model.CompanyModel

@Entity
data class CompanyEntity(
  @PrimaryKey
  val companyCode: String, // "18"
  val isInternational: Boolean, // false
  val companyName: String, // 건영택배
) : DeliveryServiceEntity<CompanyModel> {
  override fun toModel(): CompanyModel {
    return CompanyModel(companyCode, isInternational, companyName)
  }
}