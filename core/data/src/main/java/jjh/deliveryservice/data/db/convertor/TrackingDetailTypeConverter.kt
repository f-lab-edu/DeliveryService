package jjh.deliveryservice.data.db.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jjh.deliveryservice.domain.model.TrackingDetailModel

class TrackingDetailTypeConverter {
  @TypeConverter
  fun listToJson(value: List<TrackingDetailModel>?): String? = Gson().toJson(value)

  @TypeConverter
  fun jsonToList(value: String): List<TrackingDetailModel>? =
    Gson().fromJson(
      value,
      object : TypeToken<List<TrackingDetailModel>>() {}.type
    )
}