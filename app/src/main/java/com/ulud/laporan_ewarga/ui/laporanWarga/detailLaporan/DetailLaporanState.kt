package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan

import com.ulud.laporan_ewarga.domain.model.Laporan

data class DetailLaporanState(
    val laporan: Laporan? = null,
    val isPreview: Boolean = false,
    val canShowMenu: Boolean = false,
    val showImageDialog: Boolean = false,
    val selectedImageIndex: Int = 0,
    val dateText: String = "",
)