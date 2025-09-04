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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.BackButton
import com.ulud.laporan_ewarga.ui.components.tags.CategoryTag
import com.ulud.laporan_ewarga.ui.components.tags.StatusTag
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component.ImageDialog

@Composable
fun DetailLaporanScreen(
    onBack: () -> Unit = {},
    kategoriLaporan: String = "",
    judulLaporan: String = "",
    status: String = "",
    imageList: List<String> = emptyList(),
    deskripsiLaporan: String = "",
) {
    var showImageDialog by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (imageList.isNotEmpty()) {
            AsyncImage(
                model = imageList.first(),
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
                    CategoryTag(kategoriLaporan)
                    StatusTag(status)
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    modifier = Modifier.padding(horizontal = Dimen.Padding.normal),
                    text = judulLaporan,
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
                        fontSize = Dimen.FontSize.medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Preview",
                        fontSize = Dimen.FontSize.medium,
                        color = colorResource(R.color.textSecondary)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(Dimen.Padding.normal),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(imageList) { index, imgUrl ->
                        AsyncImage(
                            model = imgUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    selectedImageIndex = index
                                    showImageDialog = true
                                }
                        )
                    }
                }

                if (showImageDialog && selectedImageIndex != null) {
                    ImageDialog(
                        imageList = imageList.map { it as Any },
                        currentIndex = selectedImageIndex!!,
                        onDismiss = { showImageDialog = false },
                        onNext = {
                            if (selectedImageIndex!! < imageList.size - 1) {
                                selectedImageIndex = selectedImageIndex!! + 1
                            }
                        },
                        onPrevious = {
                            if (selectedImageIndex!! > 0) {
                                selectedImageIndex = selectedImageIndex!! - 1
                            }
                        }
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = Dimen.Padding.normal),
                    text = deskripsiLaporan,
                    fontSize = 14.sp,
                    color = Color(0xFF444444),
                    lineHeight = 20.sp
                )
                Spacer(Modifier.height(50.dp))
            }
        }

        BackButton(
            modifier = Modifier.padding(Dimen.Padding.normal),
            onBack = onBack
        )
    }
}