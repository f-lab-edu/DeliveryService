package jjh.deliveryservice.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.domain.DeliveryServiceRepository
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

  @Singleton
  @Provides
  fun provideGetDeliveryListUseCase(
    deliveryServiceRepository: DeliveryServiceRepository,
  ) = CompanyListUseCase(deliveryServiceRepository)
}