package com.ulud.laporan_ewarga.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ulud.laporan_ewarga.ui.Dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetRadioButton(
    title: String,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    options: List<String>,
    initialSelection: String?,
    onApply: (String?) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        BottomSheetRadioButtonContent(
            title = title,
            options = options,
            initialSelection = initialSelection,
            onApply = onApply
        )
    }
}

@Composable
private fun BottomSheetRadioButtonContent(
    title: String,
    options: List<String>,
    initialSelection: String?,
    onApply: (String?) -> Unit,
) {
    var tempSelection by remember(initialSelection) {
        mutableStateOf(initialSelection)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Column {
            options.forEach { option ->
                Column(
                    modifier = Modifier
                        .clickable { tempSelection = option }
                        .padding(horizontal = Dimen.Padding.normal)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimen.Padding.normal),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                        CustomRadioButton(
                            selected = (option == tempSelection),
                            onClick = { tempSelection = option }
                        )
                    }
                    HorizontalDivider(color = Color.Gray.copy(0.3f))
                }
            }

            PrimaryButton(
                modifier = Modifier
                    .padding(
                        horizontal = Dimen.Padding.normal,
                        vertical = Dimen.Padding.large
                    )
                    .fillMaxWidth(),
                text = "TERAPKAN",
                onClick = { onApply(tempSelection) }
            )
        }
    }
}
