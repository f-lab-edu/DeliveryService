package jjh.deliveryservice.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.common.BaseViewModel
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import jjh.deliveryservice.domain.usecase.DeliveryTrackingInfoUseCase
import jjh.deliveryservice.domain.usecase.RecommendCompanyListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * 택배 등록
 * */
@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val ioDispatcher: CoroutineDispatcher,
  private val companyListUseCase: CompanyListUseCase,
  private val recommendCompanyListUseCase: RecommendCompanyListUseCase,
  private val deliveryTrackingInfoUseCase: DeliveryTrackingInfoUseCase,
) : BaseViewModel() {
  private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
  val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

  private var textInput: Job? = null

  private var tempDeliveryInfo: TrackingInfoModel? = null

  var isShowCompleteAlert by mutableStateOf(false)
    private set

  init {
    exceptionHandlerCoroutine {
      val companyList = withContext(ioDispatcher) {
        companyListUseCase.invoke(false)
      }

      _uiState.update { it.copy(companyList = companyList) }
    }
  }

  /**
   * 송장번호 변경 시 호출 리스너
   *
   * @param invoiceNumber 송장번호
   * */
  fun invoiceNumberTextChangeListener(invoiceNumber: String) {
    textInput?.cancel()

    textInput = exceptionHandlerCoroutine {
      _uiState.update { it.copy(invoiceNumber = invoiceNumber) }
      delay(2000L)
    }
  }

  /**
   * 택배사 선택
   * */
  fun onCompanySelectItem(companyModel: CompanyModel) {
    _uiState.update { it.copy(selectedCompany = companyModel) }
  }

  /**
   * 택배 조회
   * */
  fun requestTrackingInfo(companyCode: String, invoiceNumber: String) {
    viewModelScope.launch {
      if (_uiState.value.trackingInfo != null && invoiceNumber == _uiState.value.invoiceNumber) {
        _uiState.update { it.copy(trackingInfo = tempDeliveryInfo) }
        tempDeliveryInfo = null
        return@launch
      }


      val data = deliveryTrackingInfoUseCase(companyCode = companyCode, invoiceNumber = invoiceNumber)
      _uiState.update { it.copy(trackingInfo = data) }
    }
  }

  /**
   * 등록할 택배이름 변경
   * */
  fun changeDeliveryItemName(name: String) {
    _uiState.update {
      it.copy(trackingInfo = it.trackingInfo?.copy(itemName = name))
    }
  }

  /**
   * 택배 저장
   * */
  fun saveDelivery(info: TrackingInfoModel) {
    exceptionHandlerCoroutine {
      deliveryTrackingInfoUseCase.saveTrackingInfo(info)
      isShowCompleteAlert = true
    }
  }

  /**
   * 입력중인 택배 취소 model bottom sheet close
   * */
  fun cancelDelivery() {

    _uiState.update {
      tempDeliveryInfo = it.trackingInfo
      it.copy(trackingInfo = null)
    }
  }
}