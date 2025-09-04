package com.ulud.laporan_ewarga.ui.laporanWarga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.CreateLaporanActivity
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import com.ulud.laporan_ewarga.ui.theme.UserRole
import splitties.activities.start

class LaporanWargaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentUserRole by remember { mutableStateOf(UserRole.WARGA) }
            LaporaneWargaTheme(currentUserRole) {
                LaporanWargaScreen(
                    onCreateLaporan = {
                        start<CreateLaporanActivity>()
                    },
                    onMenu = {
                        currentUserRole = if (currentUserRole == UserRole.WARGA) {
                            UserRole.PENGURUS
                        } else {
                            UserRole.WARGA
                        }
                    }
                )
            }
        }
    }
}