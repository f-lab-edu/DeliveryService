package jjh.deliveryservice.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.db.datastore.companyDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

  @Provides
  @Singleton
  fun providesCompanyDataStore(
    @ApplicationContext context: Context,
  ): DataStore<Preferences> = context.companyDataStore
}