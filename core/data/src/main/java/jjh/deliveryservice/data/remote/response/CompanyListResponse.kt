package jjh.deliveryservice.data.remote.response

import com.google.gson.annotations.SerializedName

data class CompanyListResponse(
  @SerializedName("Company")
  val companyList: List<CompanyResponse>,
)
