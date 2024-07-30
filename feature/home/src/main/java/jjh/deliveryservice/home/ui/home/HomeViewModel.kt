package jjh.deliveryservice.home.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.calendar.monthLastDate
import jjh.deliveryservice.common.BaseViewModel
import jjh.deliveryservice.domain.repository.DeliveryServiceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val ioDispatcher: CoroutineDispatcher,
  private val deliveryServiceRepository: DeliveryServiceRepository
) : BaseViewModel() {
  private val calendar = Calendar.getInstance()

  private val _uiState = MutableStateFlow(
    HomeUiState(yearMonthDay = CalendarUtil.getCurrentDate(calendar))
  )
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  fun getSavedTrackingInfo() {
    exceptionHandlerCoroutine(ioDispatcher) {
      val startDate = uiState.value.run { "$year.$month.1" }
      val endDate = uiState.value.run { "$year.$month.${calendar.monthLastDate}" }
      _uiState.update {
        it.copy(savedTrackingInfoList = deliveryServiceRepository.getDateDeliveryInfo(startDate, endDate))
      }
    }
  }

  fun nextMonth(): Unit = _uiState.update {
    val addMonthCalendar = calendar.apply { add(Calendar.MONTH, 1) }
    it.copy(yearMonthDay = CalendarUtil.getCurrentDate(addMonthCalendar))
  }

}