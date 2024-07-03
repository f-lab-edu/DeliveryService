package jjh.deliveryservice.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import jjh.deliveryservice.data.remote.NetworkLogger
import jjh.devlieryservice.build_config.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideRetrofit(client: OkHttpClient): DeliveryServiceApi =
    Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(DeliveryServiceApi::class.java)

  @Singleton
  @Provides
  fun provideClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .build()

  @Singleton
  @Provides
  fun provideNetworkLogger() =
    HttpLoggingInterceptor(NetworkLogger())
      .apply { this.setLevel(HttpLoggingInterceptor.Level.BODY) }

}