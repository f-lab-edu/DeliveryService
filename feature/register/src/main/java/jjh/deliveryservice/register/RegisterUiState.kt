package jjh.deliveryservice.register

import jjh.deliveryservice.domain.model.CompanyModel

data class RegisterUiState(
  val invoiceNumber: String = "",
  val companyList: List<CompanyModel> = emptyList(),
  val selectedCompany: CompanyModel? = null,
)