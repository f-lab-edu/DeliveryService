package jjh.deliveryservice.data.remote.response.companys

import com.google.gson.annotations.SerializedName
import jjh.deliveryservice.data.remote.response.DeliveryServiceResponse

data class CompanyListResponse(
  @SerializedName("Company")
  val companyList: List<CompanyResponse>,
) : DeliveryServiceResponse