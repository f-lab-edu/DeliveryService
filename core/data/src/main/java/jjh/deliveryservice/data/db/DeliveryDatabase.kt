package jjh.deliveryservice.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jjh.deliveryservice.data.db.convertor.DeliveryTypeConverter
import jjh.deliveryservice.data.db.convertor.TrackingDetailTypeConverter
import jjh.deliveryservice.data.db.dao.CompanyDao
import jjh.deliveryservice.data.db.dao.DeliveryDao
import jjh.deliveryservice.data.db.entity.CompanyEntity
import jjh.deliveryservice.data.db.entity.DeliveryEntity

@Database(entities = [DeliveryEntity::class, CompanyEntity::class], version = 1)
@TypeConverters(DeliveryTypeConverter::class, TrackingDetailTypeConverter::class)
abstract class DeliveryDatabase : RoomDatabase() {
  abstract fun deliveryDao(): DeliveryDao
  abstract fun companyDao(): CompanyDao
}