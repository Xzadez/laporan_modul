package com.ulud.laporan_ewarga.ui.laporanWarga.tabs.laporanKuTab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ulud.laporan_ewarga.ui.components.BottomSheetRadioButton
import com.ulud.laporan_ewarga.ui.laporanWarga.LaporanWargaViewModel
import com.ulud.laporan_ewarga.ui.laporanWarga.tabs.TabType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanKuTab(
    laporanViewModel: LaporanWargaViewModel = hiltViewModel(),
) {
    val state by laporanViewModel.uiState.collectAsState()
    val laporanList by laporanViewModel.filteredMyLaporan.collectAsState()
    val tabState = state.laporanKuTab

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val statusOptions = remember { listOf("Draft", "Antrian", "Proses", "Tanggapan", "Selesai") }
    val sortOptions = remember { listOf("Terbaru", "Terlama") }

    Box(modifier = Modifier.fillMaxSize()) {
        LaporanKuContent(
            laporanList = laporanList,
            textSearch = tabState.searchText,
            onTextSearchChange = { laporanViewModel.onSearchChanged(it, TabType.LAPORAN_KU) },
            selectedSortOption = tabState.selectedSort ?: "Urutkan",
            selectedStatusOption = tabState.selectedStatus ?: "Status",
            isStatusFiltered = tabState.selectedStatus != null,
            isSortFiltered = tabState.selectedSort != null,
            onFilterStatusClick = { laporanViewModel.onFilterClick(true, TabType.LAPORAN_KU) },
            onFilterSortClick = { laporanViewModel.onFilterClick(false, TabType.LAPORAN_KU) },
            onClearFiltersClick = { laporanViewModel.onClearFilters(TabType.LAPORAN_KU) },
        )

        if (tabState.isStatusSheetVisible) {
            BottomSheetRadioButton(
                title = "Status Laporan",
                onDismissRequest = { laporanViewModel.onDismissBottomSheet(TabType.LAPORAN_KU) },
                sheetState = sheetState,
                options = statusOptions,
                initialSelection = tabState.selectedStatus,
            ) { newSelection ->
                laporanViewModel.onStatusSelected(newSelection, TabType.LAPORAN_KU)
            }
        }

        if (tabState.isSortSheetVisible) {
            BottomSheetRadioButton(
                title = "Urutkan",
                onDismissRequest = { laporanViewModel.onDismissBottomSheet(TabType.LAPORAN_KU) },
                sheetState = sheetState,
                options = sortOptions,
                initialSelection = tabState.selectedSort,
            ) { newSelection ->
                laporanViewModel.onSortSelected(newSelection, TabType.LAPORAN_KU)
            }
        }
    }
}