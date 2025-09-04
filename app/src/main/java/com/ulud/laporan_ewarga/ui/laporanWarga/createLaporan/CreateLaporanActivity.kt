package com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.DetailLaporanActivity
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.LampiranLaporanActivity
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import splitties.activities.start

class CreateLaporanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var attachedPhotos by remember { mutableStateOf<List<String>>(emptyList()) }

            val attachmentLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val uris = result.data?.getStringArrayListExtra("photo_uris")
                    if (uris != null) {
                        attachedPhotos = uris
                    }
                }
            }
            LaporaneWargaTheme {
                CreateLaporanScreen(
                    attachedPhotos = attachedPhotos,
                    onBack = {
                        finish()
                    },
                    onBrowse = {
                        val intent = Intent(this, LampiranLaporanActivity::class.java)
                        intent.putStringArrayListExtra("initial_photo_uris", ArrayList(attachedPhotos))
                        attachmentLauncher.launch(intent)
                    },
                    onPrieview = { laporan ->
                        start<DetailLaporanActivity> {
                            putExtra("title", laporan.title)
                            putExtra("category", laporan.category)
                            putExtra("status", laporan.status)
                            putStringArrayListExtra("photos", ArrayList(laporan.photos))
                            putExtra("description", laporan.description)
                        }
                    }
                )
            }
        }
    }
}