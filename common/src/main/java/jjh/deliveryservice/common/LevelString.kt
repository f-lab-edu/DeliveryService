package jjh.deliveryservice.common

import androidx.annotation.StringRes
import jjh.deliveryservice.domain.model.Level
import jjh.deliveryservice.resource.R


@StringRes
fun Level.getString(): Int {
  return when (this) {
    Level.READY -> R.string.level_ready
    Level.COMPLETE_PICKUP -> R.string.level_complete_pickup
    Level.DELIVERY_PROGRESS -> R.string.level_delivery_progress
    Level.ARRIVED_BRANCH -> R.string.level_arrived_branch
    Level.DELIVERY_START -> R.string.level_delivery_start
    Level.DELIVERY_COMPLETE -> R.string.level_delivery_complete
    Level.UNKNOWN -> R.string.level_unknown
  }
}