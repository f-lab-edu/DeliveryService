package jjh.deliveryservice.data.db.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val COMPANY_PREFERENCES_NAME = "data-store_company"

// At the top level of your kotlin file:
val Context.companyDataStore by preferencesDataStore(
  name = COMPANY_PREFERENCES_NAME
)