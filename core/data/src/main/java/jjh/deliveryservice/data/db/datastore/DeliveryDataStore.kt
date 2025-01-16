package jjh.deliveryservice.data.db.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


interface DeliveryDataStore {

  val dataStorePreferences: DataStore<Preferences>

  fun stringDataStore(
    key: Preferences.Key<String>,
    defValue: String = "",
  ): DataStoreDelegate<String>

  fun intDataStore(
    key: Preferences.Key<Int>,
    defValue: Int = 0,
  ): DataStoreDelegate<Int>

  fun longDataStore(
    key: Preferences.Key<Long>,
    defValue: Long = 0L,
  ): DataStoreDelegate<Long>

  fun floatDataStore(
    key: Preferences.Key<Float>,
    defValue: Float = 0f,
  ): DataStoreDelegate<Float>

  fun booleanDataStore(
    key: Preferences.Key<Boolean>,
    defValue: Boolean = false,
  ): DataStoreDelegate<Boolean>

  fun stringSetDataStore(
    key: Preferences.Key<Set<String>>,
    defValue: Set<String> = setOf(),
  ): DataStoreDelegate<Set<String>>
}