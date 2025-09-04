package com.ulud.laporan_ewarga.domain.useCase

import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.domain.repository.LaporanRepository
import javax.inject.Inject

class DeleteLaporanUseCase @Inject constructor(
    private val repository: LaporanRepository
) {
    suspend operator fun invoke(laporan: Laporan) {
        repository.deleteLaporan(laporan)
    }
}