package jjh.deliveryservice.home

import androidx.compose.ui.util.fastJoinToString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.deliveryservice.domain.usecase.CompanyListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val companyListUseCase: CompanyListUseCase,
) : ViewModel() {

  fun get() {
    viewModelScope.launch {
      val data = companyListUseCase()
      Logger.e("data >> ${data.fastJoinToString("\n")}")
    }
  }
}