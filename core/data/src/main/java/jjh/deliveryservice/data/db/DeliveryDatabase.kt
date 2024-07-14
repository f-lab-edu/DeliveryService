package jjh.deliveryservice.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jjh.deliveryservice.data.db.entity.DeliveryEntity

@Database(entities = [DeliveryEntity::class], version = 1)
abstract class DeliveryDatabase: RoomDatabase() {
  abstract fun dao(): DeliveryDao
}