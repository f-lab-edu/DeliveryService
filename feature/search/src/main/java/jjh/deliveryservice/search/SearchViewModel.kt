package jjh.deliveryservice.search

import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.common.BaseViewModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.usecase.DeliveryTrackingInfoUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val ioDispatcher: CoroutineDispatcher,
  private val deliveryTrackingInfoUseCase: DeliveryTrackingInfoUseCase,
) : BaseViewModel() {
  private val _uiState = MutableStateFlow(SearchUiState())
  val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

  private var textInput: Job? = null

  fun changeSearchText(searchText: String) {
    textInput?.cancel()

    searchText.ifEmpty {
      _uiState.update { it.copy(searchText = "", searchedList = emptyList()) }
      return
    }

    textInput = exceptionHandlerCoroutine {
      _uiState.update { it.copy(searchText = searchText, isEmptyResult = false) }
      delay(400L)
      val nameTrackingInfo = getSearchByName(searchText)
      val invoiceTrackingInfo = getSearchByInvoiceNumber(searchText)

      val searchedList = nameTrackingInfo + invoiceTrackingInfo

      // 검색 결과가 존재하지 않는 경우
      if (searchText.isNotEmpty() && searchedList.isEmpty()) {
        _uiState.update { it.copy(isEmptyResult = true) }
        return@exceptionHandlerCoroutine
      }

      _uiState.update { it.copy(searchedList = searchedList) }
    }
  }

  private suspend fun getSearchByName(
    name: String
  ): List<TrackingInfoModel> = withContext(ioDispatcher) {
    deliveryTrackingInfoUseCase.getSearchByName(name)
  }

  private suspend fun getSearchByInvoiceNumber(
    invoiceNumber: String
  ): List<TrackingInfoModel> = withContext(ioDispatcher) {
    deliveryTrackingInfoUseCase.getSearchByInvoiceNumber(invoiceNumber)
  }
}