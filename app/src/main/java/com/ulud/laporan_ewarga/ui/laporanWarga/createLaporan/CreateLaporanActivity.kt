package com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.DetailLaporanActivity
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.LampiranLaporanActivity
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import com.ulud.laporan_ewarga.ui.theme.UserRole
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start

@AndroidEntryPoint
class CreateLaporanActivity : ComponentActivity() {

    private val viewModel: CreateLaporanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val attachmentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val uris = result.data?.getStringArrayListExtra("photo_uris")
                    if (uris != null) {
                        viewModel.onPhotosAttached(uris)
                    }
                }
            }

            val userRole by viewModel.userRole.collectAsStateWithLifecycle(initialValue = null)
            val currentRole = userRole

            if (currentRole != null) {
                LaporaneWargaTheme(currentRole) {
                    CreateLaporanScreen(
                        viewModel = viewModel,
                        onBack = {
                            finish()
                        },
                        onBrowse = {
                            val intent = Intent(this, LampiranLaporanActivity::class.java)
                            val initialPhotos = ArrayList(viewModel.uiState.value.photos)
                            intent.putStringArrayListExtra("initial_photo_uris", initialPhotos)
                            attachmentLauncher.launch(intent)
                        },
                        onPrieview = { laporan ->
                            start<DetailLaporanActivity> {
                                putExtra("title", laporan.title)
                                putExtra("category", laporan.category)
                                putExtra("status", laporan.status)
                                putStringArrayListExtra("photos", ArrayList(laporan.photos))
                                putExtra("description", laporan.description)
                                putExtra("createdAt", laporan.createdAt)
                                putExtra("isPreview", true)
                            }
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