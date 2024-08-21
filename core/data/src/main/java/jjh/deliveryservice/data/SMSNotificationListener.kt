package jjh.deliveryservice.data

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dagger.hilt.android.AndroidEntryPoint
import jjh.deliveryservice.common.getData
import jjh.deliveryservice.domain.usecase.TrackingUpdateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SMSNotificationListener : NotificationListenerService() {
  @Inject
  lateinit var updateUseCase: TrackingUpdateUseCase

  override fun onNotificationPosted(sbn: StatusBarNotification?) {
    super.onNotificationPosted(sbn)

    sbn?.notification?.extras?.apply {
      val packageName: String = sbn.packageName ?: ""

      // 핸드폰번호
      val extraTitle: String = getData(Notification.EXTRA_TITLE, String::class.java) ?: ""

      // 문자 내용
      val extraText: String = getData(Notification.EXTRA_TEXT, String::class.java) ?: ""

      if (isSmsApp(packageName)) {
        CoroutineScope(Dispatchers.IO).launch {
          updateUseCase.smsUpdate(extraText)
        }
      }
    }
  }

  private fun isSmsApp(packageName: String): Boolean {
    return packageName.run {
      equals("com.samsung.android.messaging") ||
        equals("com.android.cellbroadcastreceiver") ||
        equals("com.android.messaging") ||
        equals("com.lge.message") ||
        equals("com.htc.sense.messaging") ||
        equals("com.motorola.messaging")
    }
  }
}