package com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.PrimaryButton
import com.ulud.laporan_ewarga.ui.components.PrimaryOutlinedButton

@Composable
fun CardFooter(onPreview: () -> Unit = {}, onSubmit: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            Modifier.padding(Dimen.Padding.normal)
        ) {
            PrimaryOutlinedButton(
                text = "Lihat",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(0.6f),
                onClick = onPreview
            )
            Spacer(Modifier.width(10.dp))
            PrimaryButton(
                text = "SIMPAN",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                onClick = onSubmit,
            )
        }
    }
}