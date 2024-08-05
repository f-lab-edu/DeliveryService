package jjh.deliveryservice.data.remote.response

import jjh.deliveryservice.domain.model.DeliveryServiceModel

interface DeliveryServiceResponse<T : DeliveryServiceModel> {
  fun toModel(): T
}