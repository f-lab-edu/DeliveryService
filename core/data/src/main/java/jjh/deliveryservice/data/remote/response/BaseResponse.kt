package jjh.deliveryservice.data.remote.response

data class BaseResponse<T>(
  val data: T?,
  val code: String?,
  val msg: String?,
  val status: Boolean?,
)