package jjh.deliveryservice.data.db.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jjh.deliveryservice.data.db.entity.DeliveryEntity

class DeliveryTypeConverter {
  @TypeConverter
  fun listToJson(value: List<DeliveryEntity>): String? = Gson().toJson(value)

  @TypeConverter
  fun jsonToList(value: String): List<DeliveryEntity>? =
    Gson().fromJson(
      value,
      object : TypeToken<List<DeliveryEntity>>() {}.type
    )
}