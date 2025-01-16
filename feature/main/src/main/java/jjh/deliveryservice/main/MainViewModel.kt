package jjh.deliveryservice.main

import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.common.BaseViewModel
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import jjh.deliveryservice.domain.usecase.TrackingUpdateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val ioDispatcher: CoroutineDispatcher,
  private val companyListUseCase: CompanyListUseCase,
  private val trackingUpdateUseCase: TrackingUpdateUseCase
) : BaseViewModel() {
  fun getCompanyList() {
    exceptionHandlerCoroutine(ioDispatcher) { companyListUseCase(isFirst = true) }
  }

  fun update() {
    exceptionHandlerCoroutine(ioDispatcher) { trackingUpdateUseCase.update() }
  }

  fun saveRefreshTime(millis: Long) {
    trackingUpdateUseCase.saveRefreshTime(millis)
  }
}