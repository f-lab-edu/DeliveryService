package jjh.deliveryservice.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.Settings


fun hasNotificationAccess(context: Context): Boolean {
  return Settings.Secure.getString(
    context.applicationContext.contentResolver,
    "enabled_notification_listeners"
  ).contains(context.applicationContext.packageName)
}

fun openNotificationPermissions(context: Context) {
  try {
    val settingsIntent =
      Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
    context.startActivity(settingsIntent)
  } catch (e: ActivityNotFoundException) {
    e.printStackTrace()
  }
}