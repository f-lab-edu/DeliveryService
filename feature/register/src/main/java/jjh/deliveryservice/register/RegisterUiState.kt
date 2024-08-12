package jjh.deliveryservice.register

import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel

data class RegisterUiState(
  // TODO: 삭제 필요
  val invoiceNumber: String = "686793253375",
  val companyList: List<CompanyModel> = emptyList(),
  val selectedCompany: CompanyModel? = null,
  val trackingInfo: TrackingInfoModel? = null,
  val errorMessage: String? = null,
)