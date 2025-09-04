package com.ulud.laporan_ewarga.ui.laporanWarga.tabs.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.tags.CategoryTag
import com.ulud.laporan_ewarga.utils.FormattedDate

@Composable
fun LaporanItemCard(laporan: Laporan, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Dimen.Padding.extraSmall, top = Dimen.Padding.medium),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row (
            modifier = Modifier.clickable { onClick() }
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 120.dp)
                    .padding(Dimen.Padding.medium),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = FormattedDate(laporan.createdAt),
                    fontSize = Dimen.FontSize.extraSmall,
                    letterSpacing = 0.sp,
                    color = colorResource(R.color.textSecondary).copy(0.7f),
                    lineHeight = 1.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = laporan.title,
                        letterSpacing = 0.sp,
                        lineHeight = 14.sp,
                        fontSize = Dimen.FontSize.medium,
                        maxLines = 2
                    )
                    CategoryTag(laporan.category)
                }

            }

            AsyncImage(
                model = laporan.photos.first(),
                contentDescription = "Gambar Laporan",
                modifier = Modifier
                    .width(100.dp)
                    .heightIn(max = 120.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp, bottomStart = 0.dp,
                            topEnd = 16.dp, bottomEnd = 16.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
        }
    }
}