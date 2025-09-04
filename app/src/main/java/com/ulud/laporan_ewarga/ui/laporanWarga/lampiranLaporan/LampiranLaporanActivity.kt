package com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import com.ulud.laporan_ewarga.ui.theme.UserRole
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class LampiranLaporanActivity : ComponentActivity() {

    private val viewModel: LampiranLaporanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialPhotos =
            intent.getStringArrayListExtra("initial_photo_uris") ?: emptyList<String>()

        viewModel.initialize(initialPhotos)

        setContent {
            val userRole by viewModel.userRole.collectAsStateWithLifecycle(initialValue = null)
            val currentRole = userRole

            if (currentRole != null) {
                LaporaneWargaTheme(currentRole) {
                    LampiranLaporanScreen(
                        viewModel = viewModel,
                        onBack = { photoUris ->
                            val resultIntent = Intent()
                            resultIntent.putStringArrayListExtra("photo_uris", ArrayList(photoUris))
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
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