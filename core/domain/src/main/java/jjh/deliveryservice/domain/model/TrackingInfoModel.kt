package jjh.deliveryservice.domain.model

/**
 * 운송장 조회 결과
 *
 * @param invoiceNo 운송장 번호
 * @param companyCode 택배사 코드
 * @param name 택배명
 * @param trackingDetails 추적 상세 정보 목록
 * @param estimate 배송 예정 시간
 * @param level 진행 단계 [level 1: 배송준비중, 2: 집화완료, 3: 배송중, 4: 지점 도착, 5: 배송출발, 6:배송 완료]
 * @param registerDate 등록시간
 * */
data class TrackingInfoModel(
  val invoiceNo: String,
  val companyCode: String,
  val name: String,
  val trackingDetails: List<TrackingDetailModel>?,
  val estimate: String,
  val level: Level,
  val registerDate: String = "",
) : DeliveryServiceModel


enum class Level {
  // 1: 배송준비중, 2: 집하완료, 3: 배송중, 4: 지점 도착, 5: 배송출발, 6:배송 완료
  READY, COMPLETE_PICKUP, DELIVERY_PROGRESS, ARRIVED_BRANCH, DELIVERY_START, DELIVERY_COMPLETE, UNKNOWN;
}

fun findLevel(level: Int): Level {
  return when (level) {
    1 -> Level.READY
    2 -> Level.COMPLETE_PICKUP
    3 -> Level.DELIVERY_PROGRESS
    4 -> Level.ARRIVED_BRANCH
    5 -> Level.DELIVERY_START
    6 -> Level.DELIVERY_COMPLETE
    else -> Level.UNKNOWN
  }
}