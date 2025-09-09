package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.PrimaryButton
import com.ulud.laporan_ewarga.ui.theme.UserRole

@Composable
fun BottomButton(
    userRole: UserRole,
    isPreview: Boolean,
    onResponClick: () -> Unit
) {
    if (userRole == UserRole.PENGURUS && !isPreview) {
        Surface(
            color = Color.White,
            shadowElevation = 20.dp,
            tonalElevation = 20.dp
        ) {
            PrimaryButton(
                text = "Respon",
                onClick = onResponClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimen.Padding.normal)
            )
        }
    }
}