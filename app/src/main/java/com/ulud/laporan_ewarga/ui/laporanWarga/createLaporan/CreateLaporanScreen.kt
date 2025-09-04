package com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.ExitConfirmationDialog
import com.ulud.laporan_ewarga.ui.components.InputTextField
import com.ulud.laporan_ewarga.ui.laporanWarga.components.InputDisplayField
import com.ulud.laporan_ewarga.ui.laporanWarga.components.InputDropDown
import com.ulud.laporan_ewarga.ui.laporanWarga.components.InputTextArea
import com.ulud.laporan_ewarga.ui.components.LeadingIconType
import com.ulud.laporan_ewarga.ui.components.TopBarLaporan
import com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.components.CardFooter
import com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.components.PublicationChoice
import com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.components.PublicationDialog
import splitties.toast.UnreliableToastApi
import splitties.toast.longToast

@OptIn(UnreliableToastApi::class)
@Composable
fun CreateLaporanScreen(
    viewModel: CreateLaporanViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onBrowse: () -> Unit = {},
    onPrieview: (Laporan) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()

    var showExitDialog by remember { mutableStateOf(false) }
    var showPublicationDialog by remember { mutableStateOf(false) }

    val categories =
        listOf(
            "Kegiatan Warga",
            "Lingkungan Warga",
            "Fasilitas Umum",
            "Usulan Warga",
            "Lain - Lain"
        )

    val attachmentText = if (state.photos.isNotEmpty()) {
        "${state.photos.size} Gambar Terlampir"
    } else {
        "Ambil Gambar ..."
    }

    val attachmentColor = if (state.photos.isNotEmpty()) {
        colorResource(R.color.textPrimary)
    } else {
        colorResource(R.color.textSecondary)
    }

    val setLocation by remember { mutableStateOf<String?>("Lokasi Kejadian..") }

    BackHandler {
        showExitDialog = true
    }

    Scaffold(
        topBar = {
            TopBarLaporan(
                title = "Buat Laporan",
                leadingIconType = LeadingIconType.BACK,
                onLeadingIconClick = {
                    showExitDialog = true
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
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier.padding(
                    horizontal = Dimen.Padding.normal,
                    vertical = Dimen.Padding.large
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.Padding.medium, vertical = Dimen.Padding.large),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    InputTextField(
                        label = "Judul",
                        value = state.title,
                        onValueChange = viewModel::onTitleChange,
                        placeholder = "Masukkan judul laporan",
                    )
                    InputDisplayField(
                        label = "Lampiran",
                        color = attachmentColor,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.attach_file),
                                contentDescription = null,
                                Modifier.size(Dimen.IconSize.medium),
                                tint = Color.Black
                            )
                        },
                        value = attachmentText,
                        onClick = onBrowse
                    )
                    InputDropDown(
                        label = "Kategori Laporan",
                        value = state.category,
                        options = categories,
                        onOptionSelected = viewModel::onCategoryChange
                    )

                    InputTextArea(
                        label = "Deskripsi",
                        value = state.description,
                        onValueChange = viewModel::onDescriptionChange,
                        placeholder = "Deskripsi Laporan ...."
                    )
                    InputDisplayField(
                        label = "Tentukan Lokasi",
                        color = colorResource(R.color.textSecondary),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.location),
                                contentDescription = null,
                                Modifier.size(Dimen.IconSize.small),
                                tint = Color.Black
                            )
                        },
                        value = setLocation ?: "Tentukan titik lokasi",
                        onClick = { }
                    )
                }
            }
            CardFooter(
                onPreview = {
                    if (state.title.isBlank()) {
                        longToast("Judul laporan tidak boleh kosong.")
                        return@CardFooter
                    }
                    if (state.photos.isEmpty()) {
                        longToast("Harap lampirkan setidaknya satu gambar.")
                        return@CardFooter
                    }
                    if (state.description.isBlank()) {
                        longToast("Deskripsi laporan tidak boleh kosong.")
                        return@CardFooter
                    }
                    onPrieview(
                        Laporan(
                            title = state.title,
                            category = state.category,
                            status = "Preview",
                            photos = state.photos,
                            description = state.description
                        )
                    )
                },
                onSubmit = {
                    if (state.title.isBlank()) {
                        longToast("Judul laporan tidak boleh kosong.")
                        return@CardFooter
                    }
                    if (state.photos.isEmpty()) {
                        longToast("Harap lampirkan setidaknya satu gambar.")
                        return@CardFooter
                    }
                    if (state.description.isBlank()) {
                        longToast("Deskripsi laporan tidak boleh kosong.")
                        return@CardFooter
                    }
                    showPublicationDialog = true
                }
            )

            if (showExitDialog) {
                ExitConfirmationDialog(
                    onDismissRequest = {
                        showExitDialog = false
                    },
                    onConfirm = {
                        showExitDialog = false
                        onBack()
                    }
                )
            }

            if (showPublicationDialog) {
                PublicationDialog(
                    onDismiss = { showPublicationDialog = false },
                    onApply = { choice -> showPublicationDialog = false

                        val isDraft = choice == PublicationChoice.DRAFT

                        viewModel.saveLaporan(isDraft = isDraft)

                        longToast(if (isDraft) "Laporan disimpan sebagai draft." else "Laporan dikirim!")
                        onBack()
                    }
                )
            }
        }
    }
}