package jjh.deliveryservice.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.common.BaseViewModel
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.domain.model.TrackingInfoModel
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
  private val ioDispatcher: CoroutineDispatcher,
  private val useCase: CompanyListUseCase
) : BaseViewModel() {
  private val _uiState = MutableStateFlow(SearchDetailUiState())
  val uiState: StateFlow<SearchDetailUiState> get() = _uiState

  fun setTrackingInfoModel(trackingInfoModel: TrackingInfoModel) {
    exceptionHandlerCoroutine(context = ioDispatcher) {
      val companyModel = getCompanyModel(trackingInfoModel.companyCode)

      _uiState.update {
        it.copy(
          trackingInfoModel = trackingInfoModel,
          companyModel = companyModel
        )
      }
    }
  }

  private suspend fun getCompanyModel(companyCode: String): CompanyModel? = useCase.getCompanyModel(companyCode)

}