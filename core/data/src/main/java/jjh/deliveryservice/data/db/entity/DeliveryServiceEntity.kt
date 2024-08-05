package jjh.deliveryservice.data.db.entity

import jjh.deliveryservice.domain.model.DeliveryServiceModel

interface DeliveryServiceEntity<M : DeliveryServiceModel> {
  fun toModel(): M
}