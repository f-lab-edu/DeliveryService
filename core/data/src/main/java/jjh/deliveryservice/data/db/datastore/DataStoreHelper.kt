package jjh.deliveryservice.data.db.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreHelper<T>(
  private val getter: suspend (key: Preferences.Key<T>) -> T?,
  private val setter: suspend (key: Preferences.Key<T>, value: T) -> Unit,
) {
  suspend fun get(key: Preferences.Key<T>): T? = getter(key)
  suspend fun set(key: Preferences.Key<T>, value: T): Unit = setter(key, value)
}

fun <T> DataStore<Preferences>.helper() = DataStoreHelper(
  getter = { key: Preferences.Key<T> ->
    data.catch { t ->
      if (t is IOException) emit(emptyPreferences())
      else throw t
    }
      .map { it[key] }
      .firstOrNull()
  },

  setter = { key: Preferences.Key<T>, value: T -> edit { it[key] = value } }
)