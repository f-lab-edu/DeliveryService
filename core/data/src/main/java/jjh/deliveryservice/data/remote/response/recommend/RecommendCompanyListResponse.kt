package jjh.deliveryservice.data.remote.response.recommend

import com.google.gson.annotations.SerializedName
import jjh.deliveryservice.data.remote.response.DeliveryServiceResponse
import jjh.deliveryservice.data.remote.response.companys.CompanyResponse

data class RecommendCompanyListResponse(
  @SerializedName("Recommend")
  val companyList: List<CompanyResponse>,
) : DeliveryServiceResponse