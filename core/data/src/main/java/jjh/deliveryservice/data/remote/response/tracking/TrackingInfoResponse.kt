package jjh.deliveryservice.data.remote.response.tracking

import com.google.gson.annotations.SerializedName

/**
 * 운송장 조회 결과
 *
 * @param senderName 보내는 사람
 * @param receiverAddr 받는 사람 주소
 * @param firstDetail 첫 번째 상세 정보
 * @param level 진행 단계 [level 1: 배송준비중, 2: 집화완료, 3: 배송중, 4: 지점 도착, 5: 배송출발, 6:배송 완료]
 * @param lastDetail 마지막 상세 정보
 * @param estimate 배송 예정 시간
 * @param itemImage 상품 이미지 URL
 * @param trackingDetails 추적 상세 정보 목록
 * @param lastStateDetail 마지막 상태 상세 정보
 * @param zipCode 우편번호
 * @param invoiceNo 운송장 번호
 * @param completeYN 배송 완료 여부(Y or N)
 * @param orderNumber 주문 번호
 * @param complete 배송 완료 여부(true or false)
 * @param recipient 수령인 정보
 * @param receiverName 받는 사람
 * @param result 조회 결과
 * @param productInfo 상품 정보
 * @param itemName 상품 이름
 *
 * */
data class TrackingInfoResponse(
  val adUrl: String?,
  val complete: Boolean?,
  val invoiceNo: String?,
  val itemImage: String?,
  val itemName: String?,
  val level: Int,
  val receiverAddr: String?,
  val receiverName: String?,
  val recipient: String?,
  val result: String?,
  val senderName: String?,
  val trackingDetails: List<TrackingDetailResponse>?,
  val orderNumber: String?,
  val estimate: String?,
  val productInfo: String?,
  val zipCode: String?,
  val lastDetail: TrackingDetailResponse?,
  val lastStateDetail: TrackingDetailResponse?,
  val firstDetail: TrackingDetailResponse?,
  val completeYN: String?
)