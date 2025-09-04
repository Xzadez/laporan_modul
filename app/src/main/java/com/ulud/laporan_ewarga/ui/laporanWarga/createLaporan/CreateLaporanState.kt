package com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan

data class CreateLaporanUiState(
    val title: String = "",
    val description: String = "",
    val category: String = "Lingkungan Warga",
    val photos: List<String> = emptyList()
)
