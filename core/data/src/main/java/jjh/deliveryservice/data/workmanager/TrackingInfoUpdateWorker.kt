package jjh.deliveryservice.data.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import jjh.deliveryservice.domain.usecase.WorkManagerUseCase
import java.io.PrintWriter
import java.io.StringWriter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltWorker
class TrackingInfoUpdateWorker @AssistedInject constructor(
  @Assisted context: Context,
  @Assisted params: WorkerParameters,
  private val workManagerUseCase: WorkManagerUseCase,
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    return runCatching {
      workManagerUseCase.doWork()
      Result.success()
    }.onFailure {
      it.printStackTrace()
      Result.failure(workDataOf(ERROR_MESSAGE_KEY to it.stackTraceString))
    }.getOrDefault(Result.failure())
  }

  companion object {
    const val ERROR_MESSAGE_KEY = "ERROR_MESSAGE_KEY"

    private val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .setRequiresCharging(false)
      .build()

    val periodicWorkRequest =
      PeriodicWorkRequestBuilder<TrackingInfoUpdateWorker>(
        repeatInterval = 1,
        repeatIntervalTimeUnit = TimeUnit.DAYS
      ).setConstraints(constraints)
        .build()
  }

  private val Throwable.stackTraceString: String
    get() {
      val sw = StringWriter(256)
      val pw = PrintWriter(sw, false)
      printStackTrace(pw)
      pw.flush()
      return sw.toString()
    }
}