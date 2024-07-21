package jjh.deliveryservice.home.ui.home

import androidx.lifecycle.ViewModel
import jjh.deliveryservice.calendar.CalendarUtil
import jjh.deliveryservice.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

class HomeViewModel : BaseViewModel() {
  private val calendar = Calendar.getInstance()

  private val _uiState = MutableStateFlow(
    HomeUiState(yearMonthDay = CalendarUtil.getCurrentDate(calendar))
  )
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


  fun nextMonth(): Unit = _uiState.update {
    val addMonthCalendar = calendar.apply { add(Calendar.MONTH, 1) }
    it.copy(yearMonthDay = CalendarUtil.getCurrentDate(addMonthCalendar))
  }


}