package jjh.deliveryservice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeliveryEntity(
  @PrimaryKey
  val invoiceNo: String,
)
