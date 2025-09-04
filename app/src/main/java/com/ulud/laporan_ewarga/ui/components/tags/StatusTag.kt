package com.ulud.laporan_ewarga.ui.components.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen

@Composable
fun StatusTag(value: String = "Status") {
    Text(
        text = value,
        color = colorResource(R.color.white),
        fontSize = Dimen.FontSize.small,
        modifier = Modifier
            .background(Color.Gray, RoundedCornerShape(20.dp))
            .padding(
                horizontal = Dimen.Padding.medium,
            )
    )
}