package jjh.deliveryservice

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DeliveryServiceApplication : Application() {
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
  }
}