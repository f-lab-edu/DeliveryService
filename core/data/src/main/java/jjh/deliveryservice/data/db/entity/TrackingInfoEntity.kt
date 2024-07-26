package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level.ARRIVED_BRANCH
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level.COMPLETE_PICKUP
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level.DELIVERY_COMPLETE
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level.DELIVERY_PROGRESS
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level.DELIVERY_START
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level.READY
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Level.UNKNOWN
import jjh.deliveryservice.domain.model.TrackingDetailModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import java.util.Calendar

/**
 * @param invoiceNo 송장 번호
 * @param name 택배 이름
 * @param trackingDetails 택배 상태
 * @param estimate 도착 시간
 * @param level 배송 단계
 * @param registerDate 등록 날짜 "year.month.date"
 * */
@Entity
data class TrackingInfoEntity(
  @PrimaryKey
  val invoiceNo: String,
  val name: String,
  val trackingDetails: List<TrackingDetailModel>?,
  val estimate: String,
  val level: Level,
  val registerDate: String = "",
) : DeliveryServiceEntity<TrackingInfoModel> by Companion {

  companion object : DeliveryServiceEntity<TrackingInfoModel> {
    override fun TrackingInfoModel.toEntity(): TrackingInfoEntity {
      return TrackingInfoEntity(
        invoiceNo = invoiceNo,
        name = itemName,
        trackingDetails = trackingDetails,
        estimate = estimate,
        level = findLevel(level),
        registerDate = Calendar.getInstance().run { "$year.${month + 1}.$date" }
      )
    }
  }

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