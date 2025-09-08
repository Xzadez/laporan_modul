package com.ulud.laporan_ewarga.ui.laporanWarga

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.CreateLaporanActivity
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import com.ulud.laporan_ewarga.ui.theme.UserRole
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start

@AndroidEntryPoint
class LaporanWargaActivity : ComponentActivity() {

    private val viewModel: LaporanWargaViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentUserRole by viewModel.userRole.collectAsState()

            LaporaneWargaTheme(currentUserRole) {
                LaporanWargaScreen(
                    onCreateLaporan = {
                        start<CreateLaporanActivity>(){}
                    },
                    onMenu = {
                        viewModel.toggleUserRole()
                    }
                )
            }
        }
    }
}