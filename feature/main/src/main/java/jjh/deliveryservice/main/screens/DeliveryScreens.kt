package jjh.deliveryservice.main.screens

enum class DeliveryScreens {
  HOME, REGISTER, FIND;

  operator fun invoke(): String {
    return this.name
  }
}