package com.ulud.laporan_ewarga.ui.laporanWarga.tabs.laporanKuTab

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.PrimaryButton
import com.ulud.laporan_ewarga.ui.components.SearchTextField
import com.ulud.laporan_ewarga.ui.laporanWarga.components.EmptyLaporanMessage
import com.ulud.laporan_ewarga.ui.laporanWarga.components.FilterChip
import com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.CreateLaporanActivity
import com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.DetailLaporanActivity
import com.ulud.laporan_ewarga.ui.laporanWarga.tabs.component.LaporanItemCard
import splitties.activities.start

@Composable
fun LaporanKuContent(
    laporanList: List<Laporan>,
    textSearch: String,
    onTextSearchChange: (String) -> Unit,
    selectedStatusOption: String,
    selectedSortOption: String,
    isStatusFiltered: Boolean,
    isSortFiltered: Boolean,
    onFilterStatusClick: () -> Unit,
    onFilterSortClick: () -> Unit,
    onClearFiltersClick: () -> Unit,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.Padding.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            SearchTextField(
                value = textSearch,
                onValueChange = onTextSearchChange
            )
        }

        item {
            Spacer(Modifier.height(6.dp))
            Row(

                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isStatusFiltered || isSortFiltered) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White
                    ) {
                        IconButton(
                            onClick = onClearFiltersClick,
                            modifier = Modifier
                                .padding(Dimen.Padding.extraSmall)
                                .size(Dimen.IconSize.medium)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Bersihkan Filter",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                FilterChip(
                    text = selectedStatusOption,
                    onClick = onFilterStatusClick,
                    isFiltered = isStatusFiltered
                )
                FilterChip(
                    text = selectedSortOption,
                    onClick = onFilterSortClick,
                    isFiltered = isSortFiltered
                )
            }
        }

        if (laporanList.isEmpty()) {
            item {
                EmptyLaporanMessage(
                    title = "Tidak Ada Laporan",
                    desc = "Mohon Maaf, saat ini Anda masih belum mempunyai Laporan"
                )
                Spacer(Modifier.height(Dimen.Padding.normal))
                PrimaryButton(
                    text = "Buat Laporan",
                    leadingIcon = {
                        Icon(Icons.Default.Add, contentDescription = null)
                    },
                    onClick = {
                        val intent = Intent(context, CreateLaporanActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        } else {
            items(laporanList) { laporan ->
                LaporanItemCard(laporan, onClick = {
                    context.start<DetailLaporanActivity> {
                        putExtra("title", laporan.title)
                        putExtra("category", laporan.category)
                        putExtra("status", laporan.status)
                        putStringArrayListExtra("photos", ArrayList(laporan.photos))
                        putExtra("description", laporan.description)
                    }
                })
            }
        }
    }
}