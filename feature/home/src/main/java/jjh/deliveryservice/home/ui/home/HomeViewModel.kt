package jjh.deliveryservice.home.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.calendar.CalendarModel
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.calendar.CalendarUtil.calendarStringFormat
import jjh.deliveryservice.calendar.YearMonthDay
import jjh.deliveryservice.calendar.calendar
import jjh.deliveryservice.calendar.date
import jjh.deliveryservice.calendar.month
import jjh.deliveryservice.calendar.monthLastDate
import jjh.deliveryservice.calendar.year
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
  private val deliveryServiceRepository: DeliveryServiceRepository,
) : BaseViewModel() {
  private val calendar = Calendar.getInstance()

  private val _uiState = MutableStateFlow(
    HomeUiState(yearMonthDay = CalendarUtil.getCurrentDate(calendar))
  )
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  fun getSavedTrackingInfo() {
    exceptionHandlerCoroutine(ioDispatcher) {
      val startDate = uiState.value.run { calendarStringFormat(year, month, 1) }
      val endDate = uiState.value.run { calendarStringFormat(year, month, calendar.monthLastDate) }
      _uiState.update {
        it.copy(savedTrackingInfoList = deliveryServiceRepository.getDateDeliveryInfo(startDate, endDate))
      }
    }
  }

  fun onDateClickListener(clickedDate: CalendarModel) {
    _uiState.update {
      it.copy(
        clickedDate = clickedDate,
        homeScreenDetailExpanded = true
      )
    }
  }

  fun onDateChangeClickListener(timeInMillis: Long) {
    val calendar = calendar(timeInMillis)
    val year = calendar.year
    val month = calendar.month + 1
    val date = calendar.date

    _uiState.update {
      it.copy(
        yearMonthDay = YearMonthDay(year, month, date),
        clickedDate = CalendarModel(year, month, date, true),
        homeScreenDetailExpanded = true
      )
    }
  }

  fun homeScreenDetailStateChange(state: Boolean) {
    _uiState.update { it.copy(homeScreenDetailExpanded = state) }
  }

}