package jjh.deliveryservice.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.remote.DeliveryServiceRepositoryImpl
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindsDeliveryServiceRepository(
    deliveryServiceRepository: DeliveryServiceRepositoryImpl,
  ): DeliveryServiceRepository
}