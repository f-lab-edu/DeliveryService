package jjh.deliveryservice.main.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import jjh.deliveryservice.common.fromJson
import jjh.deliveryservice.common.toJson
import jjh.deliveryservice.detail.SearchDetailScreen
import jjh.deliveryservice.detail.SearchDetailUiState
import jjh.deliveryservice.detail.SearchDetailViewModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.home.ui.home.HomeScreen
import jjh.deliveryservice.home.ui.home.HomeUiState
import jjh.deliveryservice.home.ui.home.HomeViewModel
import jjh.deliveryservice.register.RegisterScreen
import jjh.deliveryservice.register.RegisterUiState
import jjh.deliveryservice.register.RegisterViewModel
import jjh.deliveryservice.resource.R
import jjh.deliveryservice.search.SearchScreen
import jjh.deliveryservice.search.SearchUiState
import jjh.deliveryservice.search.SearchViewModel
import jjh.deliveryservice.ui.OneButtonDialog

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
        dateArray = state.dateArray(),
        year = state.year,
        month = state.month,
        onStartSearchScreen = { navController.navigate(route = DeliveryScreens.SEARCH()) },
        onStartRegisterScreen = { navController.navigate(route = DeliveryScreens.REGISTER()) },
        onStartDetailScreen = { trackingInfoModel ->
          navController.navigate(route = DeliveryScreens.SEARCH_DETAIL() + "/${trackingInfoModel.toJson()}")
        },
        date = state.date,
        today = state.today,
        clickedDate = state.clickedDate,
        deliveryList = state.savedTrackingInfoList,
        isExpand = state.homeScreenDetailExpanded,
        homeScreenDetailStateChange = homeViewModel::homeScreenDetailStateChange,
        onDateChangeClickListener = homeViewModel::onDateChangeClickListener,
        onDateClickListener = homeViewModel::onDateClickListener,
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

      val clickedTrackingInfo: TrackingInfoModel? = navBackStackEntry.arguments
        ?.getString("clickedTrackingInfo")
        ?.fromJson<TrackingInfoModel>()

      if (clickedTrackingInfo == null) {
        OneButtonDialog(
          message = stringResource(id = R.string.not_found_search_data),
          buttonText = stringResource(id = R.string.close),
          onClick = navController::popBackStack,
          onDismissListener = {},
        )
        return@composable
      }

      val viewModel: SearchDetailViewModel = hiltViewModel()
      val state: SearchDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
      viewModel.setTrackingInfoModel(clickedTrackingInfo)

      SearchDetailScreen(
        modifier = modifier,
        searchDetailUiState = state,
//        trackingInfoModel = clickedTrackingInfo,
//        companyModel = state.companyModel,
        onBackListener = navController::popBackStack,
      )
    }
  }

}