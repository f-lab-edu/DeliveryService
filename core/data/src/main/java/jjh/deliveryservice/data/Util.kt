package jjh.deliveryservice.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.StringReader
import java.lang.reflect.Type


fun <T> getTypeTokens(): Type = object : TypeToken<T>() {}.type


fun <T> fromJson(json: String?): T? = json?.run {
  createLenientGson().fromJson(parseJsonLeniently(json), getTypeTokens<T>())
}


private fun createLenientGson(): Gson = GsonBuilder()
  .setLenient()
  .create()

fun parseJsonLeniently(json: String): JsonReader =
  JsonReader(StringReader(json)).apply { isLenient = true }
