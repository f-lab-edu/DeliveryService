package jjh.deliveryservice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jjh.deliveryservice.data.db.entity.DeliveryEntity

@Dao
interface DeliveryDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTrackingInfo(deliveryEntity: List<DeliveryEntity>)

  @Query("DELETE FROM DeliveryEntity where invoiceNo == :invoiceNo")
  suspend fun deleteTrackingInfo(invoiceNo: String)

  @Query("SELECT * FROM DeliveryEntity")
  suspend fun getAllDeliveryInfo(): List<DeliveryEntity>

  @Query("SELECT * FROM DeliveryEntity where invoiceNo == :invoiceNo")
  suspend fun getAllDeliveryInfo(invoiceNo: String): DeliveryEntity
}