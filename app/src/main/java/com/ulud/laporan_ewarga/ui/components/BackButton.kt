package com.ulud.laporan_ewarga.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ulud.laporan_ewarga.ui.Dimen

@Composable
fun BackButton(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    IconButton(
        modifier = modifier
            .background(Color.White, CircleShape)
            .size(Dimen.IconSize.large)
            .padding(Dimen.Padding.extraSmall),
        onClick = onBack
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Kembali",
            modifier = Modifier.size(Dimen.IconSize.medium)
        )
    }
}