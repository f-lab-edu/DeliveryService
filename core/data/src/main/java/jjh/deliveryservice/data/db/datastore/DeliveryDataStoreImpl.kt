package jjh.deliveryservice.data.db.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class DeliveryDataStoreImpl @Inject constructor(
  override val dataStorePreferences: DataStore<Preferences>,
) : DeliveryDataStore {

  override fun stringDataStore(
    key: Preferences.Key<String>,
    defValue: String,
  ): DataStoreDelegate<String> = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  override fun intDataStore(
    key: Preferences.Key<Int>,
    defValue: Int,
  ): DataStoreDelegate<Int> = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  override fun longDataStore(
    key: Preferences.Key<Long>,
    defValue: Long,
  ): DataStoreDelegate<Long> = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  override fun floatDataStore(
    key: Preferences.Key<Float>,
    defValue: Float,
  ): DataStoreDelegate<Float> = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  override fun booleanDataStore(
    key: Preferences.Key<Boolean>,
    defValue: Boolean,
  ): DataStoreDelegate<Boolean> = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  override fun stringSetDataStore(
    key: Preferences.Key<Set<String>>,
    defValue: Set<String>,
  ): DataStoreDelegate<Set<String>> = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )
}