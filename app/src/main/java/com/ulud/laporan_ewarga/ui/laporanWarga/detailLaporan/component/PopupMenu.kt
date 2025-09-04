package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan.component

import androidx.appcompat.widget.PopupMenu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.ui.Dimen

@Composable
fun PopupMenu(
    status: String,
    onSubmit: () -> Unit,
    onUpdate: () -> Unit,
    onDelete: () -> Unit,
    onCancel: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .background(Color.White, CircleShape)
                .size(Dimen.IconSize.large)
                .padding(Dimen.Padding.extraSmall),
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu"
            )
        }

        DropdownMenu(
            containerColor = Color.White,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            when (status.lowercase()) {
                "antrian" -> {
                    DropdownMenuItem(
                        onClick = onCancel,
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Batal Kirim",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Batal Kirim",
                                    fontSize = Dimen.FontSize.small
                                )
                            }
                        }
                    )
                }

                "draf" -> {
                    DropdownMenuItem(
                        onClick = onSubmit,
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Kirim",
                                    modifier = Modifier.size(Dimen.IconSize.small),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Kirim",
                                    fontSize = Dimen.FontSize.small
                                )
                            }
                        }
                    )

                    DropdownMenuItem(
                        onClick = onUpdate,
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Ubah",
                                    modifier = Modifier.size(Dimen.IconSize.small),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Ubah",
                                    fontSize = Dimen.FontSize.small
                                )
                            }
                        }
                    )
                    DropdownMenuItem(
                        onClick = onDelete,
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(Dimen.IconSize.small),
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Hapus",
                                    color = Color.Red,
                                    fontSize = Dimen.FontSize.small
                                )
                            }
                        }
                    )
                }

                else -> {
                }
            }
        }
    }
}