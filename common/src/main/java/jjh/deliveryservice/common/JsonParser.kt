package jjh.deliveryservice.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun <T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(): T? =
  Gson().fromJson(this, object : TypeToken<T>() {}.type)