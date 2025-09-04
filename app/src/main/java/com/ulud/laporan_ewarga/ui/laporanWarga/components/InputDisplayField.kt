package com.ulud.laporan_ewarga.ui.laporanWarga.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ulud.laporan_ewarga.ui.components.LabeledContainer


@Composable
fun InputDisplayField(
    label: String,
    value: String,
    color: Color = Color.Unspecified,
    onClick: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    LabeledContainer(
        label = label,
        onClick = onClick,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }
}