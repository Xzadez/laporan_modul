package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.BackButton

@Composable
fun DetailTopActions(
    canShowMenu: Boolean,
    status: String,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.Padding.normal),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onBack = onBack)
        if (canShowMenu) {
            PopupMenu(
                status = status,
                onSubmit = onSubmit,
                onUpdate = onUpdate,
                onDelete = onDelete
            )
        }
    }
}