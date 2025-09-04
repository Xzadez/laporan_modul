package com.ulud.laporan_ewarga.ui.laporanWarga.tabs.laporanTab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ulud.laporan_ewarga.ui.components.BottomSheetRadioButton
import com.ulud.laporan_ewarga.ui.laporanWarga.LaporanViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanTab(
    laporanViewModel: LaporanViewModel = viewModel(),
) {
    var textSearch by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState()
    val statusOptions = remember { listOf("Antrian", "Proses", "Tanggapan", "Selesai") }
    val sortOptions = remember { listOf("Terbaru", "Terlama") }

    var showStatusBottomSheet by remember { mutableStateOf(false) }
    var showSortBottomSheet by remember { mutableStateOf(false) }

    var selectedStatusOption by remember { mutableStateOf<String?>(null) }
    var selectedSortOption by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    val laporanList by laporanViewModel.publicLaporan.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LaporanContent(
            laporanList = laporanList,
            textSearch = textSearch,
            onTextSearchChange = { textSearch = it },
            selectedSortOption = selectedSortOption ?: "Urutkan",
            selectedStatusOption = selectedStatusOption ?: "Status",
            isStatusFiltered = selectedStatusOption != null,
            isSortFiltered = selectedSortOption != null,
            onFilterStatusClick = { showStatusBottomSheet = true },
            onFilterSortClick = { showSortBottomSheet = true },
            onClearFiltersClick = {
                selectedStatusOption = null
                selectedSortOption = null
            }
        )

        if (showStatusBottomSheet) {
            BottomSheetRadioButton(
                title = "Status Laporan",
                onDismissRequest = { showStatusBottomSheet = false },
                sheetState = sheetState,
                options = statusOptions,
                initialSelection = selectedStatusOption,
            ) { newSelection ->
                selectedStatusOption = newSelection
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    showStatusBottomSheet = false
                }
            }
        }

        if (showSortBottomSheet) {
            BottomSheetRadioButton(
                title = "Urutkan",
                onDismissRequest = { showSortBottomSheet = false },
                sheetState = sheetState,
                options = sortOptions,
                initialSelection = selectedSortOption,
            ) { newSelection ->
                selectedSortOption = newSelection
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    showSortBottomSheet = false
                }
            }
        }
    }
}