package com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan

import android.Manifest
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.BottomSheetList
import com.ulud.laporan_ewarga.ui.components.LeadingIconType
import com.ulud.laporan_ewarga.ui.components.TopBarLaporan
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.components.AddImage
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.components.ImageItem
import com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan.components.InfoAlert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LampiranLaporanScreen(
    viewModel: LampiranLaporanViewModel = hiltViewModel(),
    onBack: (List<String>) -> Unit = {},
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    val listOption = remember { listOf("Ambil Gambar", "Pilih Gambar dari Galeri") }
    val sheetState = rememberModalBottomSheetState()

    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.errorMessageShown()
        }
    }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            cameraImageUri?.let { viewModel.onAddPhoto(it.toString()) }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onAddPhoto(it.toString()) }
    }


    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = viewModel.createTemporaryImageUri()
            cameraImageUri = uri
            cameraLauncher.launch(uri!!)
        } else {
            Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    val sendResultAndFinish = {
        onBack(state.photos.toList())
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
                    state.photos.take(5).forEach { photoUri ->
                        val fileName = photoUri.substringAfterLast("/")
                        ImageItem(
                            text = fileName,
                            onDelete = { viewModel.onDeletePhoto(photoUri) }
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    AddImage(
                        onClick = viewModel::onAddImageClick
                    )
                }
            }
        }
    }

    if (state.showBottomSheet) {
        BottomSheetList(
            options = listOption,
            title = "Tambah Gambar",
            onClick = { selectedOption ->
                when (selectedOption) {
                    "Ambil Gambar" -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    "Pilih Gambar dari Galeri" -> galleryLauncher.launch("image/*")
                }
                viewModel.onDismissBottomSheet()
            },
            onDismissRequest = viewModel::onDismissBottomSheet,
            sheetState = sheetState
        )
    }
}