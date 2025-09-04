package com.ulud.laporan_ewarga.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ulud.laporan_ewarga.ui.Dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetList(
    title: String,
    options: List<String>,
    onClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        BottomSheetListContent(
            title = title,
            options = options,
            onClick = onClick
        )
    }
}

@Composable
private fun BottomSheetListContent(title: String, options: List<String>, onClick: (String) -> Unit = {}) {
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
                        .clickable { onClick(option) }
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
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                    }
                    HorizontalDivider(color = Color.Gray.copy(0.3f))
                }
            }
        }
    }
}

