package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level.ARRIVED_BRANCH
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level.COMPLETE_PICKUP
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level.DELIVERY_COMPLETE
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level.DELIVERY_PROGRESS
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level.DELIVERY_START
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level.READY
import jjh.deliveryservice.data.db.entity.DeliveryEntity.Level.UNKNOWN

/**
 * @param invoiceNo 송장 번호
 * @param name 택배 이름
 * @param trackingDetails 택배 상태
 * @param estimate 도착 시간
 * @param level 배송 단계
 * */
@Entity
data class DeliveryEntity(
  @PrimaryKey
  val invoiceNo: String,
  val name: String,
  val trackingDetails: List<TrackingDetailsEntity>?,
  val estimate: String,
  val level: Level,
) {

  enum class Level {
    // 1: 배송준비중, 2: 집하완료, 3: 배송중, 4: 지점 도착, 5: 배송출발, 6:배송 완료
    READY, COMPLETE_PICKUP, DELIVERY_PROGRESS, ARRIVED_BRANCH, DELIVERY_START, DELIVERY_COMPLETE, UNKNOWN;
  }
}

fun findLevel(level: Int): Level {
  return when (level) {
    1 -> READY
    2 -> COMPLETE_PICKUP
    3 -> DELIVERY_PROGRESS
    4 -> ARRIVED_BRANCH
    5 -> DELIVERY_START
    6 -> DELIVERY_COMPLETE
    else -> UNKNOWN
  }
}