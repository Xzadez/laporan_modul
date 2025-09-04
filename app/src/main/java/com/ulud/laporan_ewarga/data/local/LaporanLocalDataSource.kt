package com.ulud.laporan_ewarga.data.local

import com.ulud.laporan_ewarga.domain.model.Laporan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaporanLocalDataSource @Inject constructor() {
    private val _laporanList = MutableStateFlow<List<Laporan>>(emptyList())
    val allLaporan: Flow<List<Laporan>> = _laporanList.asStateFlow()

    fun addLaporan(laporan: Laporan) {
        _laporanList.update { currentList -> currentList + laporan }
    }

    fun updateLaporan(laporan: Laporan) {
        _laporanList.update { currentList ->
            currentList.map { if (it.id == laporan.id) laporan else it }
        }
    }

    fun deleteLaporan(laporan: Laporan) {
        _laporanList.update { currentList ->
            currentList.filterNot { it.id == laporan.id }
        }
    }
}