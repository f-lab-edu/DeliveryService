package jjh.deliveryservice.data.workermanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import jjh.deliveryservice.data.db.dao.DeliveryDao
import jjh.deliveryservice.data.db.entity.TrackingInfoEntity.Companion.toEntity
import jjh.deliveryservice.data.remote.DeliveryServiceApi
import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject

class TrackingInfoWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val deliveryServiceApi: DeliveryServiceApi,
    private val trackingDao: DeliveryDao,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {

        return runCatching {
            val resultList = trackingDao.getNotCompletedDeliveryInfo().map {
                deliveryServiceApi.trackingInfo(invoiceNumber = it.invoiceNo, code = it.invoiceNo).toEntity(companyCode = it.companyCode)
            }
            trackingDao.insertTrackingInfo(resultList)
            Result.success()
        }.onFailure {
            it.printStackTrace()
            Result.failure(workDataOf(ERROR_MESSAGE_KEY to it.stackTraceString))
        }.getOrDefault(Result.failure())
    }

    companion object {
        const val ERROR_MESSAGE_KEY = "ERROR_MESSAGE_KEY"
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