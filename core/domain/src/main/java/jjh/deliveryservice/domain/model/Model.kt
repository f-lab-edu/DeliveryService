package jjh.deliveryservice.domain.model

interface Model<R, E> {
  fun R.toModel(): Model<R, E>
  fun R.toEntity(): E
}