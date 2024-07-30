package jjh.deliveryservice.main.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import jjh.deliveryservice.register.RegisterViewModel

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

      LaunchedEffect(Unit) { homeViewModel.getSavedTrackingInfo() }

      HomeScreen(
        modifier = modifier,
        year = state.year,
        month = state.month,
        onStartRegisterScreen = { navController.navigate(route = DeliveryScreens.REGISTER()) },
        deliveryList = state.savedTrackingInfoList,
      )
    }

    composable(route = DeliveryScreens.REGISTER()) {
      val registerViewModel: RegisterViewModel = hiltViewModel()
      val state by registerViewModel.uiState.collectAsStateWithLifecycle()

      LaunchedEffect(Unit) { registerViewModel.getCompanyList() }

      RegisterScreen(
        modifier = modifier,
        isShowCompleteAlert = registerViewModel.isShowCompleteAlert,
        invoiceNumber = state.invoiceNumber,
        trackingInfoModel = state.trackingInfo,
        companyList = state.companyList,
        selectedCompany = state.selectedCompany,
        invoiceNumberTextChangeListener = registerViewModel::invoiceNumberTextChangeListener,
        itemNameTextChangeListener = registerViewModel::changeDeliveryItemName,
        onCompanySelectItem = registerViewModel::onCompanySelectItem,
        onFindClickListener = registerViewModel::requestTrackingInfo,
        onError = state.errorMessage,
        saveDelivery = registerViewModel::saveDelivery,
        cancelDelivery = registerViewModel::cancelDelivery,
        onBackListener = { navController.popBackStack() }
      )
    }

    composable(route = DeliveryScreens.FIND()) {

    }


  }

}