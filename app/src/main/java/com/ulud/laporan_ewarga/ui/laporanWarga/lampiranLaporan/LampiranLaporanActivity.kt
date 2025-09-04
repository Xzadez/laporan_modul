package com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ulud.laporan_ewarga.ui.theme.LaporaneWargaTheme
import java.util.ArrayList

class LampiranLaporanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialPhotos = intent.getStringArrayListExtra("initial_photo_uris") ?: emptyList<String>()

        setContent {
            LaporaneWargaTheme {
                LampiranLaporanScreen(
                    initialPhotos = initialPhotos,
                    onBack = { photoUris ->
                        val resultIntent = Intent()
                        resultIntent.putStringArrayListExtra("photo_uris", ArrayList(photoUris))
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                )
            }
        }
    }
}