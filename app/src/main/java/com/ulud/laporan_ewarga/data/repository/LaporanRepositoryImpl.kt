package com.ulud.laporan_ewarga.data.repository

import com.ulud.laporan_ewarga.data.local.LaporanLocalDataSource
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.domain.repository.LaporanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LaporanRepositoryImpl @Inject constructor(
    private val localDataSource: LaporanLocalDataSource
) : LaporanRepository {

    override fun getAllLaporan(): Flow<List<Laporan>> {
        return localDataSource.allLaporan
    }

    override suspend fun addLaporan(laporan: Laporan) {
        localDataSource.addLaporan(laporan)
    }

    override suspend fun updateLaporan(laporan: Laporan) {
        localDataSource.updateLaporan(laporan)
    }

    override suspend fun deleteLaporan(laporan: Laporan) {
        localDataSource.deleteLaporan(laporan)
    }

}