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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import jjh.deliveryservice.domain.model.CompanyModel
import jjh.deliveryservice.resource.CommonGreenColor
import jjh.deliveryservice.ui.DeliveryOutlineTextField
import jjh.deliveryservice.ui.Toolbar


/**
 * 택배 등록
 * */
@Composable
fun RegisterScreen(
  modifier: Modifier = Modifier,
  invoiceNumber: String = "",
  companyList: List<CompanyModel> = listOf(),
  selectedCompany: CompanyModel? = null,
  invoiceNumberTextChangeListener: (String) -> Unit = {},
  onCompanySelectItem: (CompanyModel) -> Unit = {},
  onFindClickListener: (companyCode: String, invoiceNumber: String) -> Unit = { _, _ -> },
  onBackListener: () -> Unit = {},
) {
  Column(
    modifier = modifier
      .fillMaxSize()
  ) {
    Toolbar(
      title = "택배 등록",
      startIcon = Icons.AutoMirrored.Default.ArrowBack,
      onStartClickListener = onBackListener,
    )

    Column(
      modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 24.dp, top = 10.dp)
    ) {

      // 송장번호 입력
      DeliveryOutlineTextField(
        modifier = Modifier
          .fillMaxWidth(),
        text = invoiceNumber,
        textChangeListener = invoiceNumberTextChangeListener,
        keyboardType = KeyboardType.Number,
        isError = invoiceNumber.isNotEmpty() && invoiceNumber.toLongOrNull() == null,
        placeholder = { Text(text = "송장 번호를 입력해주세요") }
      )

      // 택배사 리스트
      CompaniesComponent(
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight(),
        companyItems = companyList,
        selectedItem = selectedCompany,
        onCompanySelectItem = onCompanySelectItem
      ) // CompaniesComponent

      // 택배사 조회하기
      TextButton(
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = selectedCompany != null && invoiceNumber.isNotEmpty(),
        onClick = { onFindClickListener(selectedCompany!!.companyCode, invoiceNumber) },
        colors = ButtonColors(
          containerColor = CommonGreenColor,
          contentColor = CommonGreenColor,
          disabledContainerColor = Color.Gray.copy(alpha = 0.4f),
          disabledContentColor = Color.Gray.copy(alpha = 0.4f)
        )
      ) { Text("조회", style = TextStyle(color = Color.White)) } // TextButton
    }
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
  val list = mutableListOf<CompanyModel>()
  repeat(4) {
    list.add(CompanyModel("$it", false, "택배사$it"))
  }
  RegisterScreen(companyList = list)
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