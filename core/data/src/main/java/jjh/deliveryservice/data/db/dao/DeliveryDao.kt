package jjh.deliveryservice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jjh.deliveryservice.data.db.entity.DeliveryEntity

@Dao
interface DeliveryDao {

  @Insert
  suspend fun insertTrackingInfo(deliveryEntity: List<DeliveryEntity>)

  @Update
  suspend fun updateTrackingInfo(deliveryEntity: List<DeliveryEntity>)

  @Query("DELETE FROM DeliveryEntity where invoiceNo == :invoiceNo")
  suspend fun deleteTrackingInfo(invoiceNo: String)


}