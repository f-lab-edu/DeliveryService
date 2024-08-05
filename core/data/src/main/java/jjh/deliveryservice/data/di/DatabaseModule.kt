package jjh.deliveryservice.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jjh.deliveryservice.data.db.dao.DeliveryDao
import jjh.deliveryservice.data.db.DeliveryDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideDatabase(
    @ApplicationContext context: Context,
  ): DeliveryDatabase {
    return Room.databaseBuilder(
      context,
      DeliveryDatabase::class.java,
      "delivery_db"
    )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Provides
  fun provideDeliveryDao(db: DeliveryDatabase): DeliveryDao = db.deliveryDao()

}