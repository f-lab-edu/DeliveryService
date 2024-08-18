package jjh.deliveryservice.data.remote.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import jjh.deliveryservice.data.db.datastore.LAST_TRACKING_TIME
import jjh.deliveryservice.domain.repository.TrackingTimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class TrackingTimeRepositoryImpl @Inject constructor(
  private val dataStorePreferences: DataStore<Preferences>
) : TrackingTimeRepository {

  override suspend fun saveTrackingTime(millis: Long) {
    dataStorePreferences.edit { it[LAST_TRACKING_TIME] = millis }
  }

  override suspend fun getLastTrackingTime(): Flow<Long> = dataStorePreferences.data
    .catch { t ->
      if (t is IOException) emit(emptyPreferences())
      else throw t
    }.map { it[LAST_TRACKING_TIME] ?: System.currentTimeMillis() }

}