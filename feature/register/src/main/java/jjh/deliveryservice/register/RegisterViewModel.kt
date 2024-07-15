package jjh.deliveryservice.register

import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.common.BaseViewModel
import jjh.deliveryservice.domain.model.CompanyModel
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
  private val _state: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
  val state: StateFlow<RegisterUiState> = _state.asStateFlow()

  private var textInput: Job? = null

  init {
    exceptionHandlerCoroutine {
      val companyList = withContext(ioDispatcher) {
        companyListUseCase.invoke().filter { !it.isInternational }
      }

      _state.update { it.copy(companyList = companyList) }
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
      _state.update { it.copy(invoiceNumber = invoiceNumber) }
      delay(2000L)
    }
  }

  /**
   * 택배사 선택
   * */
  fun onCompanySelectItem(companyModel: CompanyModel) {
    _state.update { it.copy(selectedCompany = companyModel) }
  }

  fun requestTrackingInfo(companyCode: String, invoiceNumber: String) {
    exceptionHandlerCoroutine {
      deliveryTrackingInfoUseCase(companyCode = companyCode, invoiceNumber = invoiceNumber)
    }
  }
}