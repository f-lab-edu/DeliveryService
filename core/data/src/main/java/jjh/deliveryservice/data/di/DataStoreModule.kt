package jjh.deliveryservice.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.db.datastore.DeliveryDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
  private const val DELIVERY_PREFERENCES_NAME = "data-store_delivery"
  private val Context.deliveryDataStore by preferencesDataStore(
    name = DELIVERY_PREFERENCES_NAME
  )

  @Provides
  @Singleton
  fun providesCompanyDataStore(
    @ApplicationContext context: Context,
  ): DataStore<Preferences> = context.deliveryDataStore


  @Provides
  @Singleton
  fun providesDeliveryDataStore(
    dataStorePreferences: DataStore<Preferences>,
  ): DeliveryDataStore = DeliveryDataStore(dataStorePreferences)
}