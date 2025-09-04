package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import com.ulud.laporan_ewarga.ui.theme.UserRole
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailLaporanActivity : ComponentActivity() {

    private val viewModel: DetailLaporanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val laporan = Laporan(
            title = intent.getStringExtra("title") ?: "",
            category = intent.getStringExtra("category") ?: "",
            status = intent.getStringExtra("status") ?: "",
            photos = intent.getStringArrayListExtra("photos") ?: emptyList(),
            description = intent.getStringExtra("description") ?: "",
            createdAt = intent.getLongExtra("createdAt", 0L)
        )
        val isPreview = intent.getBooleanExtra("isPreview", false)
        val canShowMenu = intent.getBooleanExtra("can_show_menu", false)

        viewModel.initialize(laporan, isPreview, canShowMenu)

        setContent {
            val userRole by viewModel.userRole.collectAsStateWithLifecycle(initialValue = null)

            val currentRole = userRole

            if (currentRole != null) {
                LaporaneWargaTheme(currentRole) {
                    DetailLaporanScreen(
                        viewModel = viewModel,
                        onBack = { finish() },
                        onDelete = {
                            viewModel.onDelete()
                            finish()
                        },
                        onSubmit = {
                            viewModel.onSubmitDraft()
                            finish()
                        },
                        onLihatResponClick = {
                            Toast.makeText(this, "Membuka halaman respon...", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}