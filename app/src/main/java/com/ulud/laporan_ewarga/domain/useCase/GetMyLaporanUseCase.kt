package com.ulud.laporan_ewarga.domain.useCase

import com.ulud.laporan_ewarga.domain.repository.LaporanRepository
import com.ulud.laporan_ewarga.domain.model.Laporan
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyLaporanUseCase @Inject constructor(
    private val repository: LaporanRepository
) {
    operator fun invoke(): Flow<List<Laporan>> {
        return repository.getAllLaporan()
    }
}