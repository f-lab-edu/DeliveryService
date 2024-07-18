package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param invoiceNo 송장 번호
 * @param
 * */
@Entity
data class DeliveryEntity(
  @PrimaryKey
  val invoiceNo: String,
  val trackingDetails: List<TrackingDetailsEntity>?,
  val estimate: String,
  val level: Level,
) {
  enum class Level {
    // 1: 배송준비중, 2: 집하완료, 3: 배송중, 4: 지점 도착, 5: 배송출발, 6:배송 완료
    READY, COMPLETE_PICKUP, DELIVERY_PROGRESS, ARRIVED_BRANCH, DELIVERY_START, DELIVERY_COMPLETE
  }
}