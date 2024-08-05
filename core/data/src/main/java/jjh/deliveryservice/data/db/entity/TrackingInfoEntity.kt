package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import jjh.deliveryservice.calendar.CalendarUtil.calendarStringFormat
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.domain.model.Level
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
) {

  fun toModel(): TrackingInfoModel {
    return TrackingInfoModel(
      invoiceNo = invoiceNo,
      name = name,
      trackingDetails = trackingDetails,
      estimate = estimate,
      level = level,
      registerDate = Calendar.getInstance().run {
        calendarStringFormat(this.year, this.month + 1, this.date)
      }
    )
  }

  companion object {
    fun TrackingInfoModel.toEntity(): TrackingInfoEntity {
      return TrackingInfoEntity(
        invoiceNo = invoiceNo,
        name = name,
        trackingDetails = trackingDetails,
        estimate = estimate,
        level = level,
        registerDate = registerDate
      )
    }
  }
}