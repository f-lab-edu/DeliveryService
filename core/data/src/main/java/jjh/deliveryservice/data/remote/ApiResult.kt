package jjh.deliveryservice.data.remote

import jjh.deliveryservice.data.remote.response.CommonResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed interface ApiResult<out T> {
  sealed interface Success<T> : ApiResult<T> {
    data class ResponseSuccess<T>(val data: T) : Success<T>
    object Empty : Success<Nothing>
  }

  sealed class Fail(val errorMessage: String) : ApiResult<Nothing> {
    data class ResponseFail(
      val code: String? = null,
      val message: String? = null,
      val errorCode: Boolean? = null,
    ) : Fail(if (errorCode != null) "[$errorCode] $message" else "$message")

    data class NetworkError(val code: Int? = null, val message: String? = null) : Fail("[$code] $message")
    data class Exception(val t: Throwable) : Fail("${t.message}")
  }
}

fun <T> safeFlow(apiFunc: suspend () -> CommonResponse<T>): Flow<ApiResult<T>> = flow {
  try {
    val response = apiFunc.invoke()
    if (response.code == "0" || response.code == "1") { // 통신 성공시
      val contents = if (response.contents == null) { // Return Data가 없는 경우
        ApiResult.Success.Empty
      } else {
        ApiResult.Success.ResponseSuccess<T>(response.contents)
      }
      emit(contents)
    } else { // 통신 실패시

      emit(ApiResult.Fail.ResponseFail(response.code, response.msg, response.status))
    }
  } catch (e: HttpException) { // 네트워크 오류시
    emit(ApiResult.Fail.NetworkError(e.code(), e.message()))
  } catch (e: SocketTimeoutException) {
    emit(ApiResult.Fail.NetworkError(-1, "TIME_OUT"))
  } catch (e: SocketException) {
    emit(ApiResult.Fail.NetworkError(-1, "SOCKET_WRITE_ERROR"))
  } catch (e: UnknownHostException) { // 네트워크 연결 끊겼을 경우
    emit(ApiResult.Fail.NetworkError(-1, "UNKNOWN_HOST"))
  }
}