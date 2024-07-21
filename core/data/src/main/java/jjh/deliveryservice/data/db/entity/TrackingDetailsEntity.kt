package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 진행 상세
 *
 * @param remark 비고
 * @param level 진행 단계
 * @param manName 배송 기사 이름
 * @param where 진행 위치 지점
 * @param code 배송 상태 코드
 * @param time 진행 시간
 * @param manPic 배송 기사 전화번호
 * @param kind 진행 상태
 * @param telNo 진행 위치(지점) 전화번호
 * @param telNo2 배송 기사 전화번호
 * @param timeString 진행 시간
 * */
@Entity
data class TrackingDetailsEntity(
  @PrimaryKey
  val level: Int?, // 2

  val remark: String?,

  val manName: String?,

  val where: String?, // 글로벌직구팀직영(신현호)

  val code: String?,

  val time: Long?, // 1714044794000

  val manPic: String?,

  val kind: String?, // 집화처리

  val telNo: String?, // 010-3350-5902

  val telNo2: String?,

  val timeString: String?, // 2024-04-25 20:33:14
)