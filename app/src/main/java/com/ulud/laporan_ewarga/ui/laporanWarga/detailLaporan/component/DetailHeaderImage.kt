package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ulud.laporan_ewarga.R

@Composable
fun DetailHeaderImage(photos: List<Any>) {
    if (photos.isNotEmpty()) {
        AsyncImage(
            model = photos.first(),
            contentDescription = "Gambar utama laporan",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Gambar placeholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}