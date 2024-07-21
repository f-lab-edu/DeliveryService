package jjh.deliveryservice.data.db.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jjh.deliveryservice.data.db.entity.TrackingDetailsEntity

class TrackingDetailTypeConverter {
  @TypeConverter
  fun listToJson(value: List<TrackingDetailsEntity>?): String? = Gson().toJson(value)

  @TypeConverter
  fun jsonToList(value: String): List<TrackingDetailsEntity>? =
    Gson().fromJson(
      value,
      object : TypeToken<List<TrackingDetailsEntity>>() {}.type
    )
}