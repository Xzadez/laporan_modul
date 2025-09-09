package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.PrimaryButton
import com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.components.ResponLaporanDialog
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component.BottomButton
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component.DetailContentBody
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component.DetailHeaderImage
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component.DetailTopActions
import com.ulud.laporan_ewarga.ui.theme.UserRole

@Composable
fun DetailLaporanScreen(
    viewModel: DetailLaporanViewModel = hiltViewModel(),
    userRole: UserRole,
    onBack: () -> Unit = {},
    onSubmit: () -> Unit = {},
    onUpdate: () -> Unit = {},
    onDelete: () -> Unit = {},
    onLihatResponClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val laporan = state.laporan ?: return

    if (showDialog) {
        ResponLaporanDialog(
            namaLaporan = "Jalan Rusak di Samping Kantor Pos",
            onDismissRequest = {
                showDialog = false
            },
            onProsesClick = {
                println("Laporan sedang diproses...")
                showDialog = false
            }
        )
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (userRole == UserRole.PENGURUS && !state.isPreview) {
                Surface(
                    color = Color.White,
                    shadowElevation = 20.dp,
                    tonalElevation = 20.dp
                ) {
                    PrimaryButton(
                        "Respon",
                        onClick = {
                            showDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimen.Padding.normal)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            DetailHeaderImage(photos = laporan.photos)
            DetailContentBody(
                modifier = Modifier.padding(innerPadding),
                laporan = laporan,
                state = state,
                onDismiss = viewModel::onDismissImageDialog,
                onNext = viewModel::onNextImage,
                onPrevious = viewModel::onPreviousImage,
                onImageClick = { index -> viewModel.onImageClicked(index) },
                onLihatResponClick = onLihatResponClick
            )
            DetailTopActions(
                canShowMenu = state.canShowMenu,
                status = laporan.status,
                onBack = onBack,
                onSubmit = onSubmit,
                onUpdate = onUpdate,
                onDelete = onDelete
            )
        }
    }
}