package com.ulud.laporan_ewarga.ui.laporanWarga

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ulud.laporan_ewarga.data.repository.LaporanRepository
import com.ulud.laporan_ewarga.domain.model.Laporan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LaporanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LaporanRepository(application)

    val publicLaporan: StateFlow<List<Laporan>> = repository.allLaporan
        .map { list -> list.filter { !it.isDraft } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val myLaporan: StateFlow<List<Laporan>> = repository.allLaporan
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addLaporan(laporan: Laporan) {
        viewModelScope.launch {
            repository.addLaporan(laporan)
        }
    }
}