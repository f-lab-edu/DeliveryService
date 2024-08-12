package jjh.deliveryservice

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import jjh.deliveryservice.data.workmanager.TrackingInfoUpdateWorker.Companion.periodicWorkRequest
import javax.inject.Inject

@HiltAndroidApp
class DeliveryServiceApplication : Application(), Configuration.Provider {

  @Inject
  lateinit var workerFactory: HiltWorkerFactory

  override fun onCreate() {
    super.onCreate()

    // 로깅
    val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
      .showThreadInfo(false)
      .methodCount(0)
      .methodOffset(7)
      .tag("로그 결과: ")
      .build()

    Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {

      override fun isLoggable(priority: Int, tag: String?): Boolean {
        return true
      }
    })

    WorkManager
      .getInstance(this)
      .enqueueUniquePeriodicWork(
        "TrackingInfoUpdate",
        ExistingPeriodicWorkPolicy.REPLACE,
        periodicWorkRequest
      )
  }

  override fun getWorkManagerConfiguration(): Configuration {
    return Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .build()
  }
}