package com.ulud.laporan_ewarga.ui.laporanWarga

import com.ulud.laporan_ewarga.ui.laporanWarga.tabs.TabState

data class LaporanWargaState(
    val laporanTab: TabState = TabState(),
    val laporanKuTab: TabState = TabState()
)