package jjh.deliveryservice.register

import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel

data class RegisterUiState(
  val invoiceNumber: String = "",
  val companyList: List<CompanyModel> = emptyList(),
  val selectedCompany: CompanyModel? = null,
  val trackingInfo: TrackingInfoModel? = null,
  val errorMessage: String? = null,
)