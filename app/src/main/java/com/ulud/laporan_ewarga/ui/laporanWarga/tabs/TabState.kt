package com.ulud.laporan_ewarga.ui.laporanWarga.tabs

data class TabState(
    val searchText: String = "",
    val selectedStatus: String? = null,
    val selectedSort: String? = null,
    val isStatusSheetVisible: Boolean = false,
    val isSortSheetVisible: Boolean = false
)

enum class TabType {
    LAPORAN_PUBLIK,
    LAPORAN_KU
}
