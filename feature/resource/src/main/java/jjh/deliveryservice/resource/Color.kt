package jjh.deliveryservice.resource

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val CommonGreenColor = Color(0x88075500)

val CommonGreenColor2 = Color(0xFF075500)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun deliveryDatePickerColor(
  containerColor: Color = Color.Unspecified,
  titleContentColor: Color = Color.Unspecified,
  headlineContentColor: Color = Color.Unspecified,
  weekdayContentColor: Color = Color.Unspecified,
  subheadContentColor: Color = Color.Unspecified,
  yearContentColor: Color = Color.Unspecified,
  selectedYearContentColor: Color = Color.Unspecified,
  dayContentColor: Color = Color.Unspecified,
  disabledDayContentColor: Color = Color.Unspecified,
  disabledSelectedDayContentColor: Color = Color.Unspecified,
  disabledSelectedDayContainerColor: Color = Color.Unspecified,
  dayInSelectionRangeContainerColor: Color = Color.Unspecified,
): DatePickerColors =
  DatePickerDefaults.colors(
    selectedDayContainerColor = CommonGreenColor,
    selectedDayContentColor = Color.White,
    selectedYearContainerColor = CommonGreenColor,
    currentYearContentColor = CommonGreenColor,
    todayContentColor = CommonGreenColor,
    todayDateBorderColor = CommonGreenColor,
    dayInSelectionRangeContentColor = CommonGreenColor,

    containerColor = containerColor,
    titleContentColor = titleContentColor,
    headlineContentColor = headlineContentColor,
    weekdayContentColor = weekdayContentColor,
    subheadContentColor = subheadContentColor,
    yearContentColor = yearContentColor,
    selectedYearContentColor = selectedYearContentColor,
    dayContentColor = dayContentColor,
    disabledDayContentColor = disabledDayContentColor,
    disabledSelectedDayContentColor = disabledSelectedDayContentColor,
    disabledSelectedDayContainerColor = disabledSelectedDayContainerColor,
    dayInSelectionRangeContainerColor = dayInSelectionRangeContainerColor,
  )

@Composable
fun TextFieldDefaults.emptyColors(
  focusedTextColor: Color = Color.Transparent,
  unfocusedTextColor: Color = Color.Transparent,
  disabledTextColor: Color = Color.Transparent,
  errorTextColor: Color = Color.Transparent,
  focusedContainerColor: Color = Color.Transparent,
  unfocusedContainerColor: Color = Color.Transparent,
  disabledContainerColor: Color = Color.Transparent,
  errorContainerColor: Color = Color.Transparent,
  cursorColor: Color = Color.Transparent,
  errorCursorColor: Color = Color.Transparent,
  selectionColors: TextSelectionColors = TextSelectionColors(handleColor = Color(0xFF4286F4), backgroundColor = Color(0xFF4286F4).copy(alpha = 0.4f)),
  focusedIndicatorColor: Color = Color.Transparent,
  unfocusedIndicatorColor: Color = Color.Transparent,
  disabledIndicatorColor: Color = Color.Transparent,
  errorIndicatorColor: Color = Color.Transparent,
  focusedLeadingIconColor: Color = Color.Transparent,
  unfocusedLeadingIconColor: Color = Color.Transparent,
  disabledLeadingIconColor: Color = Color.Transparent,
  errorLeadingIconColor: Color = Color.Transparent,
  focusedTrailingIconColor: Color = Color.Transparent,
  unfocusedTrailingIconColor: Color = Color.Transparent,
  disabledTrailingIconColor: Color = Color.Transparent,
  errorTrailingIconColor: Color = Color.Transparent,
  focusedLabelColor: Color = Color.Transparent,
  unfocusedLabelColor: Color = Color.Transparent,
  disabledLabelColor: Color = Color.Transparent,
  errorLabelColor: Color = Color.Transparent,
  focusedPlaceholderColor: Color = Color.Transparent,
  unfocusedPlaceholderColor: Color = Color.Transparent,
  disabledPlaceholderColor: Color = Color.Transparent,
  errorPlaceholderColor: Color = Color.Transparent,
  focusedSupportingTextColor: Color = Color.Transparent,
  unfocusedSupportingTextColor: Color = Color.Transparent,
  disabledSupportingTextColor: Color = Color.Transparent,
  errorSupportingTextColor: Color = Color.Transparent,
  focusedPrefixColor: Color = Color.Transparent,
  unfocusedPrefixColor: Color = Color.Transparent,
  disabledPrefixColor: Color = Color.Transparent,
  errorPrefixColor: Color = Color.Transparent,
  focusedSuffixColor: Color = Color.Transparent,
  unfocusedSuffixColor: Color = Color.Transparent,
  disabledSuffixColor: Color = Color.Transparent,
  errorSuffixColor: Color = Color.Transparent,
) = colors(
  focusedTextColor = focusedTextColor,
  unfocusedTextColor = unfocusedTextColor,
  disabledTextColor = disabledTextColor,
  errorTextColor = errorTextColor,
  focusedContainerColor = focusedContainerColor,
  unfocusedContainerColor = unfocusedContainerColor,
  disabledContainerColor = disabledContainerColor,
  errorContainerColor = errorContainerColor,
  cursorColor = cursorColor,
  errorCursorColor = errorCursorColor,
  selectionColors = selectionColors,
  focusedIndicatorColor = focusedIndicatorColor,
  unfocusedIndicatorColor = unfocusedIndicatorColor,
  disabledIndicatorColor = disabledIndicatorColor,
  errorIndicatorColor = errorIndicatorColor,
  focusedLeadingIconColor = focusedLeadingIconColor,
  unfocusedLeadingIconColor = unfocusedLeadingIconColor,
  disabledLeadingIconColor = disabledLeadingIconColor,
  errorLeadingIconColor = errorLeadingIconColor,
  focusedTrailingIconColor = focusedTrailingIconColor,
  unfocusedTrailingIconColor = unfocusedTrailingIconColor,
  disabledTrailingIconColor = disabledTrailingIconColor,
  errorTrailingIconColor = errorTrailingIconColor,
  focusedLabelColor = focusedLabelColor,
  unfocusedLabelColor = unfocusedLabelColor,
  disabledLabelColor = disabledLabelColor,
  errorLabelColor = errorLabelColor,
  focusedPlaceholderColor = focusedPlaceholderColor,
  unfocusedPlaceholderColor = unfocusedPlaceholderColor,
  disabledPlaceholderColor = disabledPlaceholderColor,
  errorPlaceholderColor = errorPlaceholderColor,
  focusedSupportingTextColor = focusedSupportingTextColor,
  unfocusedSupportingTextColor = unfocusedSupportingTextColor,
  disabledSupportingTextColor = disabledSupportingTextColor,
  errorSupportingTextColor = errorSupportingTextColor,
  focusedPrefixColor = focusedPrefixColor,
  unfocusedPrefixColor = unfocusedPrefixColor,
  disabledPrefixColor = disabledPrefixColor,
  errorPrefixColor = errorPrefixColor,
  focusedSuffixColor = focusedSuffixColor,
  unfocusedSuffixColor = unfocusedSuffixColor,
  disabledSuffixColor = disabledSuffixColor,
  errorSuffixColor = errorSuffixColor,
)

