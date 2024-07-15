package jjh.deliveryservice.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.domain.usecase.DeliveryTrackingInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import jjh.deliveryservice.domain.usecase.CompanyListUseCase

@HiltViewModel
class MainViewModel @Inject constructor(
  private val useCase: CompanyListUseCase,
  private val deliveryTrackingInfoUseCase: DeliveryTrackingInfoUseCase,
) : ViewModel() {
  init {
    viewModelScope.launch { useCase(isFirst = true) }
  }
}