package jjh.deliveryservice.register

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jjh.deliveryservice.domain.model.CompanyModel


/**
 * 택배 등록
 * */
@Composable
fun RegisterScreen(
  modifier: Modifier = Modifier,
  viewModel: RegisterViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  RegisterComponent(
    modifier = modifier,
    uiState = state,
    invoiceNumberTextChangeListener = viewModel::invoiceNumberTextChangeListener,
    onCompanySelectItem = viewModel::onCompanySelectItem,
    onFindClickListener = viewModel::requestTrackingInfo
  )
}

@Composable
fun RegisterComponent(
  modifier: Modifier = Modifier,
  uiState: RegisterUiState = RegisterUiState(),
  invoiceNumberTextChangeListener: (String) -> Unit = {},
  onCompanySelectItem: (CompanyModel) -> Unit = {},
  onFindClickListener: (companyCode: String, invoiceNumber: String) -> Unit = { _, _ -> },
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(vertical = 30.dp, horizontal = 16.dp)
  ) {

    // 송장번호 입력
    OutlinedTextField(
      modifier = Modifier
        .fillMaxWidth(),
      shape = RoundedCornerShape(10.dp),
      placeholder = { Text(text = "송장 번호를 입력해주세요") },
      colors = TextFieldDefaults.colors(
        disabledContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
      ),
      textStyle = TextStyle(color = Color(0xFF075500)),
      isError = uiState.invoiceNumber.isNotEmpty() && uiState.invoiceNumber.toLongOrNull() == null,
      singleLine = true,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      prefix = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
      value = uiState.invoiceNumber,
      onValueChange = invoiceNumberTextChangeListener
    ) // OutlinedTextField

    // 택배사 리스트
    CompaniesComponent(
      modifier = Modifier
        .weight(1f)
        .fillMaxHeight(),
      companyItems = uiState.companyList,
      selectedItem = uiState.selectedCompany,
      onCompanySelectItem = onCompanySelectItem
    ) // CompaniesComponent

    // 택배사 조회하기
    TextButton(
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp),
      shape = RoundedCornerShape(10.dp),
      enabled = uiState.selectedCompany != null && uiState.invoiceNumber.isNotEmpty(),
      onClick = { onFindClickListener(uiState.selectedCompany!!.companyCode, uiState.invoiceNumber) },
      colors = ButtonColors(
        containerColor = Color(0xFF075500),
        contentColor = Color(0xFF075500),
        disabledContainerColor = Color.Gray.copy(alpha = 0.4f),
        disabledContentColor = Color.Gray.copy(alpha = 0.4f)
      )
    ) { Text("조회", style = TextStyle(color = Color.White)) } // TextButton
  }
}

@Composable
fun CompaniesComponent(
  modifier: Modifier = Modifier,
  companyItems: List<CompanyModel> = listOf(),
  selectedItem: CompanyModel? = null,
  onCompanySelectItem: (CompanyModel) -> Unit = {},
) {
  val rememberedItems by rememberUpdatedState(newValue = companyItems)

  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(2)
  ) {
    itemsIndexed(
      items = rememberedItems,
      key = { _, item -> item.companyName + item.companyCode }
    ) { index, item ->

      val (color, stroke) =
        selectedColorAndStroke(isSelected = selectedItem == item)

      CompanyItem(
        modifier = Modifier
          .height(60.dp)
          .padding(vertical = 5.dp)
          .padding(start = if (index % 2 != 0) 10.dp else 0.dp)
          .border(stroke, color = color, shape = RoundedCornerShape(10.dp)),
        text = item.companyName,
        onCompanySelectItem = { onCompanySelectItem(item) }
      )
    }
  }
}

@Composable
fun CompanyItem(
  modifier: Modifier = Modifier,
  text: String,
  onCompanySelectItem: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .clickable(onClick = onCompanySelectItem),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      text = text,
      textAlign = TextAlign.Center
    )
  }
}


@Preview(showBackground = true)
@Composable
private fun RegisterComponentPreview() {
  RegisterComponent(uiState = RegisterUiState(companyList = companies))
}

@Preview(showBackground = true)
@Composable
private fun CompaniesComponentPreview() {
  CompaniesComponent(companyItems = companies)
}

private val companies = listOf(
  CompanyModel(
    "code1",
    true,
    companyName = "asdf"
  ),
  CompanyModel(
    "code2",
    true,
    companyName = "asdf"
  ),
  CompanyModel(
    "code3",
    true,
    companyName = "asdf"
  ),
  CompanyModel(
    "code4",
    true,
    companyName = "asdf"
  ),
  CompanyModel(
    "code5",
    true,
    companyName = "asdf"
  ),
  CompanyModel(
    "code6",
    true,
    companyName = "asdf"
  ),
)