package jjh.deliveryservice.main.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import jjh.deliveryservice.common.fromJson
import jjh.deliveryservice.common.toJson
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.home.ui.home.HomeScreen
import jjh.deliveryservice.home.ui.home.HomeUiState
import jjh.deliveryservice.home.ui.home.HomeViewModel
import jjh.deliveryservice.register.RegisterScreen
import jjh.deliveryservice.register.RegisterUiState
import jjh.deliveryservice.register.RegisterViewModel
import jjh.deliveryservice.search.SearchDetailScreen
import jjh.deliveryservice.search.SearchScreen
import jjh.deliveryservice.search.SearchUiState
import jjh.deliveryservice.search.SearchViewModel

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
      val state: HomeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

      LaunchedEffect(Unit) { homeViewModel.getSavedTrackingInfo() }

      HomeScreen(
        modifier = modifier,
        year = state.year,
        month = state.month,
        onStartSearchScreen = { navController.navigate(route = DeliveryScreens.SEARCH()) },
        onStartRegisterScreen = { navController.navigate(route = DeliveryScreens.REGISTER()) },
        deliveryList = state.savedTrackingInfoList,
      )
    }

    composable(route = DeliveryScreens.REGISTER()) {
      val registerViewModel: RegisterViewModel = hiltViewModel()
      val state: RegisterUiState by registerViewModel.uiState.collectAsStateWithLifecycle()

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
        onBackListener = navController::popBackStack
      )
    }

    composable(route = DeliveryScreens.SEARCH()) {
      val searchViewModel: SearchViewModel = hiltViewModel()
      val state: SearchUiState by searchViewModel.uiState.collectAsStateWithLifecycle()

      SearchScreen(
        modifier = modifier,
        searchText = state.searchText,
        trackingInfoList = state.searchedList,
        isEmptyResult = state.isEmptyResult,
        onBackListener = navController::popBackStack,
        onItemClickListener = { clickedTrackingInfo ->
          navController.navigate(route = DeliveryScreens.SEARCH_DETAIL() + "/${clickedTrackingInfo.toJson()}")
        },
        onValueChange = searchViewModel::changeSearchText,
      )
    }

    composable(
      route = DeliveryScreens.SEARCH_DETAIL() + "/{clickedTrackingInfo}",
      arguments = listOf(navArgument("clickedTrackingInfo") { type = NavType.StringType })
    ) { navBackStackEntry ->

      val clickedTrackingInfo: TrackingInfoModel =
        navBackStackEntry.arguments
          ?.getString("clickedTrackingInfo")
          ?.fromJson<TrackingInfoModel>()
          ?: return@composable

      SearchDetailScreen(
        modifier = modifier,
        trackingInfoModel = clickedTrackingInfo,
        onBackListener = navController::popBackStack,
      )
    }


  }

}