package jjh.deliveryservice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity

@Dao
interface DeliveryDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTrackingInfo(trackingInfoEntity: List<TrackingInfoEntity>)

  @Query("DELETE FROM TrackingInfoEntity where invoiceNo == :invoiceNo")
  suspend fun deleteTrackingInfo(invoiceNo: String)

  @Query("SELECT * FROM TrackingInfoEntity")
  suspend fun getAllDeliveryInfo(): List<TrackingInfoEntity>

  @Query("SELECT * FROM TrackingInfoEntity WHERE level != 6")
  suspend fun getNotCompletedDeliveryInfo(): List<TrackingInfoEntity>

  @Query("SELECT * FROM TrackingInfoEntity WHERE registerDate BETWEEN :startDate AND :endDate")
  suspend fun getDateDeliveryInfo(startDate: String, endDate: String): List<TrackingInfoEntity>

  @Query("SELECT * FROM TrackingInfoEntity where invoiceNo == :invoiceNo")
  suspend fun getDeliveryInfo(invoiceNo: String): TrackingInfoEntity


  @Query("SELECT * FROM TrackingInfoEntity where name LIKE '%' || :name || '%'")
  suspend fun getDeliveryInfoByName(name: String): List<TrackingInfoEntity>

  @Query("SELECT * FROM TrackingInfoEntity where invoiceNo LIKE '%' || :invoiceNo || '%'")
  suspend fun getDeliveryInfoByInvoiceNo(invoiceNo: String): List<TrackingInfoEntity>

}