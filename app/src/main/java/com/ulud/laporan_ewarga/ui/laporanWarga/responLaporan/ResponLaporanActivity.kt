package com.ulud.laporan_ewarga.ui.laporanWarga.responLaporan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.LampiranLaporanViewModel
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResponLaporanActivity : ComponentActivity() {
    private val viewModel: LampiranLaporanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val userRole by viewModel.userRole.collectAsStateWithLifecycle(initialValue = null)
            val currentRole = userRole

            if (currentRole != null) {
                LaporaneWargaTheme(currentRole) {
                    ResponLaporanScreen(
                        onBack = {finish()}
                    )
                }
            }
        }
    }
}