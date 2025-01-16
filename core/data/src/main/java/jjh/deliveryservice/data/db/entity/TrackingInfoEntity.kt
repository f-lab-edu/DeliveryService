package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import jjh.deliveryservice.calendar.CalendarUtil.calendarStringFormat
import jjh.deliveryservice.calendar.calendar
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.year
import jjh.deliveryservice.domain.model.TrackingDetailModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.model.findLevel
import java.util.Calendar

/**
 * @param invoiceNo 송장 번호
 * @param name 택배 이름
 * @param trackingDetails 택배 상태
 * @param estimate 도착 시간
 * @param level 배송 단계
 * @param registerDate 등록 날짜 "year.month.date"
 * @param companyCode 회사 코드
 * */
@Entity
data class TrackingInfoEntity(
  @PrimaryKey
  val invoiceNo: String,
  val name: String,
  val trackingDetails: List<TrackingDetailModel>,
  val estimate: String,
  val level: Int,
  val registerDate: String = "",
  val companyCode: String,
) {
  fun toModel(): TrackingInfoModel {
    return TrackingInfoModel(
      invoiceNo = invoiceNo,
      name = name,
      trackingDetails = trackingDetails,
      estimate = estimate,
      level = findLevel(level),
      registerDate = registerDate.ifEmpty { calendarStringFormat() },
      companyCode = companyCode
    )
  }

  companion object {
    fun TrackingInfoModel.toEntity(): TrackingInfoEntity {
      return TrackingInfoEntity(
        invoiceNo = invoiceNo,
        name = name,
        trackingDetails = getTrackingDetails(),
        estimate = estimate,
        level = level.ordinal,
        registerDate = registerDate.ifEmpty { calendarStringFormat() },
        companyCode = companyCode
      )
    }
  }
}