package jjh.deliveryservice.main.screens

enum class DeliveryScreens {
  HOME, REGISTER, SEARCH, SEARCH_DETAIL;

  operator fun invoke(): String {
    return this.name
  }
}