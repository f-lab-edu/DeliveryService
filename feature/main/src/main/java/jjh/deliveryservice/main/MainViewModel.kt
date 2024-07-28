package jjh.deliveryservice.main

import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.common.BaseViewModel
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import jjh.deliveryservice.domain.usecase.DeliveryTrackingInfoUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val companyListUseCase: CompanyListUseCase,
  private val deliveryTrackingInfoUseCase: DeliveryTrackingInfoUseCase,
) : BaseViewModel() {
  fun getCompanyList() {
    exceptionHandlerCoroutine { companyListUseCase(isFirst = true) }
  }
}