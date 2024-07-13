package jjh.deliveryservice.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.domain.repository.DeliveryServiceRepositoryImpl
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

  @Singleton
  @Provides
  fun provideGetDeliveryListUseCase(
    deliveryServiceApi: DeliveryServiceApi,
  ) = CompanyListUseCase(DeliveryServiceRepositoryImpl(deliveryServiceApi))
}