package jjh.deliveryservice.main.screens

enum class DeliveryScreens {
  HOME, REGISTER, SEARCH;

  operator fun invoke(): String {
    return this.name
  }
}