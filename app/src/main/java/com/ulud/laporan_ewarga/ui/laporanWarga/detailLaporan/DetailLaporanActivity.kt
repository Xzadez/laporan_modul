package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme

class DetailLaporanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val judul = intent.getStringExtra("title") ?: ""
        val kategoriLaporan = intent.getStringExtra("category") ?: ""
        val status = intent.getStringExtra("status") ?: ""
        val photos = intent.getStringArrayListExtra("photos") ?: emptyList()
        val deskripsiLaporan = intent.getStringExtra("description") ?: ""
        setContent {
            LaporaneWargaTheme {
                DetailLaporanScreen(
                    onBack = {
                        finish()
                    },
                    kategoriLaporan = kategoriLaporan,
                    judulLaporan = judul,
                    status = status,
                    imageList = photos,
                    deskripsiLaporan = deskripsiLaporan
                )
            }
        }
    }
}