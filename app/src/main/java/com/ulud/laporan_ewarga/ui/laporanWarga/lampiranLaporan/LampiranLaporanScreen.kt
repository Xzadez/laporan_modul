package com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.BottomSheetList
import com.ulud.laporan_ewarga.ui.components.PrimaryButton
import com.ulud.laporan_ewarga.ui.laporanWarga.components.LeadingIconType
import com.ulud.laporan_ewarga.ui.laporanWarga.components.TopBarLaporan
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.components.AddImage
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.components.ImageItem
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.components.InfoAlert
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LampiranLaporanScreen(initialPhotos: List<String>, onBack: (List<String>) -> Unit = {}) {
    val context = LocalContext.current
    val photos = remember { mutableStateListOf(*initialPhotos.toTypedArray()) }
    val listOption = remember { listOf("Ambil Gambar", "Pilih Gambar dari Galeri") }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            cameraImageUri?.let {
                if (photos.size < 5) photos.add(it.toString())
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (photos.size < 5) photos.add(it.toString())
        }
    }

    fun createImageUri(context: Context, customName: String? = null): Uri? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = customName ?: "laporan_$timeStamp"

        val imageFile = File(context.cacheDir, "$fileName.jpg").apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createImageUri(context)
            cameraImageUri = uri
            cameraLauncher.launch(uri!!)
        } else {
            Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    val sendResultAndFinish = {
        onBack(photos.toList())
    }

    BackHandler {
        sendResultAndFinish()
    }

    Scaffold(
        topBar = {
            TopBarLaporan(
                title = "Tambahkan Gambar Lampiran",
                leadingIconType = LeadingIconType.BACK,
                onLeadingIconClick = {
                    sendResultAndFinish()
                },
                colors = Color.White,
                showLeadingIconBorder = true
            )
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.white)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(
                    horizontal = Dimen.Padding.normal,
                    vertical = Dimen.Padding.medium
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.padding(vertical = Dimen.Padding.medium),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimen.Padding.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    InfoAlert(
                        message = "Lampiran dapat berupa file berformat JPG, JPEG, PNG, atau PDF, dengan ukuran maksimal 1,5 MB dan maksimal 5 file"
                    )
                    Spacer(Modifier.height(4.dp))
                    photos.take(5).forEach { photoUri ->
                        val fileName = photoUri.substringAfterLast("/")
                        ImageItem(
                            text = fileName,
                            onDelete = { photos.remove(photoUri) }
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    AddImage(
                        onClick = {

                            showBottomSheet = true
                        }
                    )
                }
            }
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "SIMPAN",
                onClick = {
                },
                enabled = false
            )
        }
    }

    if (showBottomSheet) {
        BottomSheetList(
            options = listOption,
            title = "Tambah Gambar",
            onClick = { selectedOption ->
                when (selectedOption) {
                    "Ambil Gambar" -> {
                        if (photos.size < 5) {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }

                    "Pilih Gambar dari Galeri" -> {
                        if (photos.size < 5) {
                            galleryLauncher.launch("image/*")
                        }
                    }
                }
                showBottomSheet = false
            },
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        )
    }
}
