package com.ulud.laporan_ewarga.ui.components.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen

@Composable
fun CategoryTag(value: String = "Kategori") {
    Text(
        text = value,
        color = MaterialTheme.colorScheme.primary,
        fontSize = Dimen.FontSize.extraSmall,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = Dimen.Padding.medium,
            )
    )
}