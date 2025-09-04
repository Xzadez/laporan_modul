package com.ulud.laporan_ewarga.ui.laporanWarga.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.components.LabeledContainer

@Composable
fun InputDropDown(
    label: String,
    value: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var containerWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box {
        LabeledContainer(
            label = label,
            onClick = { isExpanded = true },
            leadingIcon = leadingIcon,
            modifier = Modifier.onGloballyPositioned { coordinates ->
                containerWidth = with(density) { coordinates.size.width.toDp() }
            },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.width(containerWidth),
            containerColor = Color.White,
            shape = RoundedCornerShape(8.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = colorResource(R.color.textPrimary)) },
                    onClick = {
                        onOptionSelected(option)
                        isExpanded = false
                    }
                )
            }
        }
    }
}