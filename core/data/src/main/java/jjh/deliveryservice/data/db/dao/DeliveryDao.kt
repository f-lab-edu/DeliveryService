package jjh.deliveryservice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jjh.deliveryservice.data.db.entity.DeliveryEntity

@Dao
interface DeliveryDao {

  @Insert
  suspend fun insertDeliveryInfo(deliveryEntity: DeliveryEntity)

  @Update
  suspend fun updateDeliveryInfo(deliveryEntity: DeliveryEntity)

  @Query("DELETE FROM DeliveryEntity where invoiceNo == :invoiceNo")
  suspend fun deleteDeliveryInfo(invoiceNo: String)


}