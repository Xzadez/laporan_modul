package com.ulud.laporan_ewarga.domain.repository

import com.ulud.laporan_ewarga.domain.model.Laporan
import kotlinx.coroutines.flow.Flow

interface LaporanRepository {
    fun getAllLaporan(): Flow<List<Laporan>>
    suspend fun addLaporan(laporan: Laporan)
    suspend fun updateLaporan(laporan: Laporan)
    suspend fun deleteLaporan(laporan: Laporan)
}