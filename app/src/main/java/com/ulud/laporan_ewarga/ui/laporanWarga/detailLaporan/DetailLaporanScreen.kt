package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.BackButton
import com.ulud.laporan_ewarga.ui.components.PrimaryButton
import com.ulud.laporan_ewarga.ui.components.tags.CategoryTag
import com.ulud.laporan_ewarga.ui.components.tags.StatusTag
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component.ImageDialog
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component.PopupMenu
import com.ulud.laporan_ewarga.utils.FormattedDate

@Composable
fun DetailLaporanScreen(
    viewModel: DetailLaporanViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onSubmit: () -> Unit = {},
    onUpdate: () -> Unit = {},
    onDelete: () -> Unit = {},
    onLihatResponClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val laporan = state.laporan ?: return

    val dateText = when {
        state.isPreview -> "Preview"
        laporan.status.equals("Draft", ignoreCase = true) -> "Draft"
        else -> FormattedDate(laporan.createdAt)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (laporan.photos.isNotEmpty()) {
            AsyncImage(
                model = laporan.photos.first(),
                contentDescription = "Gambar utama laporan",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Gambar placeholder",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(220.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = Dimen.Padding.normal)

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.Padding.normal)
                ) {
                    CategoryTag(laporan.category)
                    StatusTag(laporan.status)
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    modifier = Modifier.padding(horizontal = Dimen.Padding.normal),
                    text = laporan.title,
                    fontSize = Dimen.FontSize.large,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.textPrimary)
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.Padding.normal)
                ) {
                    Text(
                        text = "Ahmad Nashruddin",
                        fontSize = Dimen.FontSize.small,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = dateText,
                        fontSize = Dimen.FontSize.small,
                        color = colorResource(R.color.textSecondary)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(Dimen.Padding.normal),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(laporan.photos) { index, imgUrl ->
                        AsyncImage(
                            model = imgUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.onImageClicked(index)
                                }
                        )
                    }
                }

                if (state.showImageDialog) {
                    ImageDialog(
                        imageList = laporan.photos.map { it as Any },
                        currentIndex = state.selectedImageIndex,
                        onDismiss = viewModel::onDismissImageDialog,
                        onNext = viewModel::onNextImage,
                        onPrevious = viewModel::onPreviousImage
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = Dimen.Padding.normal),
                    text = laporan.description,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                val status = laporan.status.lowercase()
                if (status !in listOf("antrian", "draft") && !state.isPreview) {
                    Spacer(Modifier.height(Dimen.Padding.large))
                    PrimaryButton(
                        text = "Lihat Respon",
                        onClick = onLihatResponClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimen.Padding.normal)
                    )
                }

                Spacer(Modifier.height(50.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Padding.normal),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onBack = onBack)
            if (state.canShowMenu) {
                PopupMenu(
                    status = laporan.status,
                    onSubmit = onSubmit,
                    onUpdate = onUpdate,
                    onDelete = onDelete
                )
            }
        }
    }
}