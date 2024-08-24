package jjh.deliveryservice.data.db.datastore

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias DataStoreProperty<T> = ReadWriteProperty<Any?, T>

class DataStoreDelegate<T>(
  private val key: Preferences.Key<T>,
  private val defValue: T,
  private val helper: DataStoreHelper<T>,
) : DataStoreProperty<T> {
  private val coroutineScope = CoroutineScope(Dispatchers.IO)

  override fun getValue(thisRef: Any?, property: KProperty<*>): T = runBlocking {
    val value = helper.get(key) ?: defValue
    Logger.d("DataStore Get [${key.name}] $value")
    value
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    coroutineScope.launch {
      Logger.d("DataStore Set [${key.name}] $value")
      helper.set(key, value)
    }
  }
}