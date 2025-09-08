package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.PrimaryButton
import com.ulud.laporan_ewarga.ui.components.tags.CategoryTag
import com.ulud.laporan_ewarga.ui.components.tags.StatusTag
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.DetailLaporanState

@Composable
fun DetailContentBody(
    modifier: Modifier = Modifier,
    laporan: Laporan,
    state: DetailLaporanState,
    onDismiss:() -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onImageClick: (Int) -> Unit,
    onLihatResponClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
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
                    text = state.dateText,
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
                            .clickable { onImageClick(index) }
                    )
                }
            }

            if (state.showImageDialog) {
                ImageDialog(
                    imageList = laporan.photos.map { it as Any },
                    currentIndex = state.selectedImageIndex,
                    onDismiss = onDismiss,
                    onNext = onNext,
                    onPrevious = onPrevious
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(horizontal = Dimen.Padding.normal),
                text = laporan.description,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            if (state.isLihatResponButtonVisible) {
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
}
