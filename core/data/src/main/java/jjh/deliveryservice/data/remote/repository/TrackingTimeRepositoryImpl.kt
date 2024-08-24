package jjh.deliveryservice.data.remote.repository

import jjh.deliveryservice.data.db.datastore.DeliveryDataStore
import jjh.deliveryservice.data.db.datastore.LAST_TRACKING_TIME
import jjh.deliveryservice.data.db.datastore.REFRESH_TIME
import jjh.deliveryservice.domain.repository.TrackingTimeRepository
import javax.inject.Inject

class TrackingTimeRepositoryImpl @Inject constructor(
  dataStore: DeliveryDataStore,
) : TrackingTimeRepository {

  private var prefRefreshTime by dataStore.longDataStore(REFRESH_TIME)
  private var prefLastTrackingTime by dataStore.longDataStore(LAST_TRACKING_TIME)

  override fun saveTrackingTime(millis: Long) {
    prefLastTrackingTime = millis
  }

  override fun getLastTrackingTime(): Long = prefLastTrackingTime

  override fun saveRefreshTime(millis: Long) {
    prefRefreshTime = millis
  }

  override fun getRefreshTime(): Long = prefRefreshTime

}