package com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan

data class LampiranLaporanState(
    val photos: List<String> = emptyList(),
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)