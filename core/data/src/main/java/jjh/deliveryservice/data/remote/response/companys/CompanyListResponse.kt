package jjh.deliveryservice.data.remote.response.companys

import com.google.gson.annotations.SerializedName

data class CompanyListResponse(
  @SerializedName("Company")
  val companyList: List<CompanyResponse>,
)