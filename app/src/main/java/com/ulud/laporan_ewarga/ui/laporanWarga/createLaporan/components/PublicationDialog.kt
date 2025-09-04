package com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.CustomRadioButton


@Composable
fun PublicationDialog(
    onDismiss: () -> Unit,
    onApply: (PublicationChoice) -> Unit,
) {
    var selectedChoice by remember { mutableStateOf<PublicationChoice?>(null) }

    val isChoiceSelected = selectedChoice != null
    val dialogTitle = when (selectedChoice) {
        PublicationChoice.PUBLISH_NOW -> "Kirim Laporan Sekarang"
        PublicationChoice.DRAFT -> "Simpan Sebagai Draf"
        null -> "Konfirmasi Kirim Laporan"
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(Dimen.Padding.normal),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = dialogTitle,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Tutup",
                                tint = Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Pilih Kirim Sekarang apabila ingin laporan langsung dipublikasikan, atau pilih Draft (Kirim Laporan nanti)",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ChoiceOption(
                        choice = PublicationChoice.PUBLISH_NOW,
                        selected = selectedChoice == PublicationChoice.PUBLISH_NOW,
                        title = "Kirim Sekarang",
                        description = "Jika Anda sudah yakin dengan laporan yang Anda buat, pilih opsi \"Kirim Sekarang\" untuk mengirim laporan secara langsung.",
                        onClick = {
                            selectedChoice = if (selectedChoice == PublicationChoice.PUBLISH_NOW) {
                                null
                            } else {
                                PublicationChoice.PUBLISH_NOW
                            }
                        }
                    )
                    ChoiceOption(
                        choice = PublicationChoice.DRAFT,
                        selected = selectedChoice == PublicationChoice.DRAFT,
                        title = "Draf (nanti)",
                        description = "Jika Anda masih ragu untuk mengirim laporan Anda, silakan pilih opsi \"draf\" untuk menyimpan laporan tanpa mengirimnya.",
                        onClick = {
                            selectedChoice = if (selectedChoice == PublicationChoice.DRAFT) {
                                null
                            } else {
                                PublicationChoice.DRAFT
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        selectedChoice?.let { onApply(it) }
                    },
                    enabled = isChoiceSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C897),
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Text(text = "TERAPKAN", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ChoiceOption(
    choice: PublicationChoice,
    selected: Boolean,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) colorResource(R.color.backgroundColor) else Color.Transparent,
        label = "ChoiceBackground"
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) colorResource(R.color.primaryColor) else Color.LightGray,
        label = "ChoiceBorder"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(Dimen.Padding.medium),
    ) {
        CustomRadioButton(
            modifier = Modifier.size(16.dp),
            selected = selected,
            onClick = onClick,
            selectedColor = colorResource(R.color.primaryColor)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                style = MaterialTheme.typography.titleSmall,
                lineHeight = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = Dimen.FontSize.small,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall

            )
        }
    }
}