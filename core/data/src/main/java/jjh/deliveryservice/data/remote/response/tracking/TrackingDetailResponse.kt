package jjh.deliveryservice.data.remote.response.tracking

import com.google.gson.annotations.SerializedName

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
 *
 * */
data class TrackingDetailResponse(
  val remark: String, 

  val level: Int, 

  @SerializedName("manName")
  val manName: String, 

  val where: String,

  val code: String, 

  val time: Long,

  val manPic: String, 

  val kind: String,

  @SerializedName("telno")
  val telNo: String, 

  @SerializedName("telno2")
  val telNo2: String, 

  val timeString: String, 
)