package jjh.deliveryservice.ui

import android.content.Context


val Context.getDisplayWidth: Int
  get() = resources.displayMetrics.widthPixels

val Context.getDisplayHeight: Int
  get() = resources.displayMetrics.heightPixels

