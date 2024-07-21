package jjh.deliveryservice.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.db.dao.CompanyDao
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import jjh.deliveryservice.domain.repository.DeliveryServiceRepositoryImpl
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import jjh.deliveryservice.domain.usecase.DeliveryTrackingInfoUseCase
import jjh.deliveryservice.domain.usecase.RecommendCompanyListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

  @Singleton
  @Provides
  fun provideDeliveryServiceRepository(
    deliveryServiceApi: DeliveryServiceApi,
    dao: CompanyDao,
  ): DeliveryServiceRepository = DeliveryServiceRepositoryImpl(deliveryServiceApi, dao)

  @Singleton
  @Provides
  fun provideGetDeliveryListUseCase(
    deliveryServiceRepository: DeliveryServiceRepository,
  ) = CompanyListUseCase(deliveryServiceRepository)

  @Singleton
  @Provides
  fun provideGetRecommendDeliveryListUseCase(
    deliveryServiceRepository: DeliveryServiceRepository,
  ) = RecommendCompanyListUseCase(deliveryServiceRepository)

  @Singleton
  @Provides
  fun provideGetTrackingInfoUseCase(
    deliveryServiceRepository: DeliveryServiceRepository,
  ) = DeliveryTrackingInfoUseCase(deliveryServiceRepository)
}