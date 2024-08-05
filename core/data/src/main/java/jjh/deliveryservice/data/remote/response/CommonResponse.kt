package jjh.deliveryservice.data.remote.response

/**
 * @param code 성공, 실패코드
 * @param msg 에러 메시지
 * @param status 상태
 * @param contents 데이터
 * */
data class CommonResponse<T>(
  val code: String?,
  val msg: String?,
  val status: Boolean?,
  val contents: T?,
)