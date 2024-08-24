package jjh.deliveryservice.data.db.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject


class DeliveryDataStore @Inject constructor(
  private val dataStorePreferences: DataStore<Preferences>,
) {

  fun stringDataStore(
    key: Preferences.Key<String>,
    defValue: String = "",
  ) = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  fun intDataStore(
    key: Preferences.Key<Int>,
    defValue: Int = 0,
  ) = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  fun longDataStore(
    key: Preferences.Key<Long>,
    defValue: Long = 0L,
  ) = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  fun floatDataStore(
    key: Preferences.Key<Float>,
    defValue: Float = 0f,
  ) = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  fun booleanDataStore(
    key: Preferences.Key<Boolean>,
    defValue: Boolean = false,
  ) = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )

  fun stringSetDataStore(
    key: Preferences.Key<Set<String>>,
    defValue: Set<String> = setOf(),
  ) = DataStoreDelegate(
    key = key,
    defValue = defValue,
    helper = dataStorePreferences.helper()
  )
}