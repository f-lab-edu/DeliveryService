package jjh.deliveryservice.domain.model

data class TrackingDetailModel(
  val remark: String,
  val level: Int, // 2
  val manName: String,
  val where: String, // 글로벌직구팀직영(신현호)
  val code: String,
  val time: Long, // 1714044794000
  val manPic: String,
  val kind: String, // 집화처리
  val telNo: String, // 010-3350-5902
  val telNo2: String,
  val timeString: String, // 2024-04-25 20:33:14
) : DeliveryServiceModel {
  companion object {
    fun init() = TrackingDetailModel(
      remark = "",
      level = 0,
      manName = "",
      where = "",
      code = "",
      time = 0,
      manPic = "",
      kind = "",
      telNo = "",
      telNo2 = "",
      timeString = "-",
    )
  }
}