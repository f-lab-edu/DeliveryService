package jjh.deliveryservice.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.DeliveryServiceRepositoryImpl
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.domain.DeliveryServiceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Singleton
  @Provides
  fun provideDeliveryServiceRepository(
    deliveryServiceApi: DeliveryServiceApi,
  ): DeliveryServiceRepository =
    DeliveryServiceRepositoryImpl(deliveryServiceApi)
}