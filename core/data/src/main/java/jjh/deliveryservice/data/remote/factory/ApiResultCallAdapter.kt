/*
package jjh.deliveryservice.data.remote.factory

import jjh.deliveryservice.data.remote.ApiResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ApiResultCallAdapter<R>(
  private val successType: Type
) : CallAdapter<R, Call<ApiResult<R>>> {
  override fun adapt(call: Call<R>): Call<ApiResult<R>> = ApiResultCall(call, successType)

  override fun responseType(): Type = successType
}*/
