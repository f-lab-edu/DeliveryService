//package jjh.deliveryservice.data.remote.factory
//
//import jjh.deliveryservice.data.remote.ApiResult
//import okhttp3.Request
//import okio.IOException
//import okio.Timeout
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.lang.reflect.Type
//
//class ApiResultCall<R>(
//  private val delegate: Call<R>,
//  private val successType: Type
//) : Call<ApiResult<R>> {
//
//  override fun enqueue(callback: Callback<ApiResult<R>>) = delegate.enqueue(
//    object : Callback<R> {
//
//      override fun onResponse(call: Call<R>, response: Response<R>) {
//        callback.onResponse(this@ApiResultCall, Response.success(response.toApiResult()))
//      }
//
//      private fun Response<R>.toApiResult(): ApiResult<R> {
//        // Http 에러 응답
//        if (!isSuccessful) {
//          val errorBody = errorBody()!!.string()
//          return ApiResult.Failure.HttpError(
//            code = code(),
//            message = message(),
//            body = errorBody
//          )
//        }
//
//        // Body가 존재하는 Http 성공 응답
//        body()?.let { body -> return ApiResult.successOf(body) }
//
//        // successType이 Unit인 경우 Body가 존재하지 않더라도 성공으로 간주합니다.
//        return if (successType == Unit::class.java) {
//          @Suppress("UNCHECKED_CAST")
//          ApiResult.successOf(Unit as R)
//        } else {
//          ApiResult.Failure.Exception(
//            IllegalStateException(
//              "Body가 존재하지 않지만, Unit 이외의 타입으로 정의했습니다. ApiResult<Unit>로 정의하세요."
//            )
//          )
//        }
//      }
//
//      override fun onFailure(call: Call<R?>, throwable: Throwable) {
//        val error = if (throwable is IOException) {
//          ApiResult.Failure.NetworkError(throwable)
//        } else {
//          ApiResult.Failure.UnknownApiError(throwable)
//        }
//        callback.onResponse(this@ApiResultCall, Response.success(error))
//      }
//    }
//  )
//
//  override fun clone(): Call<ApiResult<R>> = ApiResultCall(delegate.clone(), successType)
//
//  override fun execute(): Response<ApiResult<R>> {
//    TODO("Not yet implemented")
//  }
//
//  override fun isExecuted(): Boolean {
//    TODO("Not yet implemented")
//  }
//
//  override fun cancel() {
//    TODO("Not yet implemented")
//  }
//
//  override fun isCanceled(): Boolean {
//    TODO("Not yet implemented")
//  }
//
//  override fun request(): Request {
//    TODO("Not yet implemented")
//  }
//
//  override fun timeout(): Timeout {
//    TODO("Not yet implemented")
//  }
//}