package jjh.deliveryservice.data.db.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val DELIVERY_PREFERENCES_NAME = "data-store_delivery"

val Context.deliveryDataStore by preferencesDataStore(
  name = DELIVERY_PREFERENCES_NAME
)