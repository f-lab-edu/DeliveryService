package jjh.deliveryservice.main.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jjh.deliveryservice.home.ui.home.HomeScreen

@Composable
fun DeliveryNavHost(
  modifier: Modifier = Modifier,
  navController: NavHostController,
) {
  NavHost(
    navController = navController,
    startDestination = DeliveryScreens.HOME(),
  ) {
    composable(route = DeliveryScreens.HOME()) {
      HomeScreen(
        modifier = modifier,
        onStartRegisterScreen = { navController.navigate(route = DeliveryScreens.REGISTER()) }
      )
    }

    composable(route = DeliveryScreens.REGISTER()) {

    }

    composable(route = DeliveryScreens.FIND()) {

    }


  }

}