//package jjh.deliveryservice.data.remote.factory
//
//import jjh.deliveryservice.data.remote.ApiResult
//import retrofit2.Call
//import retrofit2.CallAdapter
//import retrofit2.Retrofit
//import java.lang.reflect.ParameterizedType
//import java.lang.reflect.Type
//
//class DeliveryCallAdapterFactory : CallAdapter.Factory() {
//  override fun get(
//    returnType: Type,
//    annotations: Array<out Annotation>,
//    retrofit: Retrofit,
//  ): CallAdapter<*, *>? {
//    // ParameterizedType ~> Collection<String>
//    // Raw Type: 제네릭 타입의 원래 클래스. Collection
//    // Type Arguments: 제네릭 타입의 매개변수. String.
//    if (getRawType(returnType) != Call::class.java) return null
//    check(returnType is ParameterizedType) {
//      "Return 타입은 ApiResult<Foo> 또는 ApiResult<out Foo>로 정의되어야 합니다."
//    }
//
//    // getParameterUpperBound: 제네릭에 선언된 타입의 인덱스
//    // ex) Map<String, Runnable> ~> 0인덱스 String, 1인덱스 Runnable
//    // Call<ApiResult<???>> 타입으로 날아오기 때문에 0번째 ~> ApiResult<???>
//    val wrapperType = getParameterUpperBound(0, returnType)
//    if (getRawType(wrapperType) != ApiResult::class.java) return null
//    check(wrapperType is ParameterizedType) {
//      "Return 타입은 ApiResult<ResponseBody>로 정의되어야 합니다."
//    }
//
//    val bodyType = getParameterUpperBound(0, wrapperType)
//    return ApiResultCallAdapter<Any>(bodyType)
//  }
//}