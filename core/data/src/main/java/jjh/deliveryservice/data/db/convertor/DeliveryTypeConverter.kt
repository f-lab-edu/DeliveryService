package jjh.deliveryservice.data.db.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity

class DeliveryTypeConverter {
  @TypeConverter
  fun listToJson(value: List<TrackingInfoEntity>): String? = Gson().toJson(value)

  @TypeConverter
  fun jsonToList(value: String): List<TrackingInfoEntity>? =
    Gson().fromJson(
      value,
      object : TypeToken<List<TrackingInfoEntity>>() {}.type
    )
}