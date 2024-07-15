package jjh.deliveryservice.main.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jjh.deliveryservice.home.ui.home.HomeScreen
import jjh.deliveryservice.home.ui.home.HomeViewModel
import jjh.deliveryservice.register.RegisterScreen

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
      val homeViewModel: HomeViewModel = hiltViewModel()
      val state by homeViewModel.uiState.collectAsStateWithLifecycle()

      HomeScreen(
        modifier = modifier,
        year = state.year,
        month = state.month,
        onStartRegisterScreen = { navController.navigate(route = DeliveryScreens.REGISTER()) }
      )
    }

    composable(route = DeliveryScreens.REGISTER()) {
      RegisterScreen(
        modifier = modifier
      )
    }

    composable(route = DeliveryScreens.FIND()) {

    }


  }

}