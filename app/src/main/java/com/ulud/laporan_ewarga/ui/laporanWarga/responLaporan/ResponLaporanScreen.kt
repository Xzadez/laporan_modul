package com.ulud.laporan_ewarga.ui.laporanWarga.responLaporan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.domain.model.Chat
import com.ulud.laporan_ewarga.ui.Dimen
import com.ulud.laporan_ewarga.ui.components.LeadingIconType
import com.ulud.laporan_ewarga.ui.components.TopBarLaporan
import com.ulud.laporan_ewarga.ui.components.TrailingIconType
import com.ulud.laporan_ewarga.ui.laporanWarga.responLaporan.components.ChatInputField
import com.ulud.laporan_ewarga.ui.laporanWarga.responLaporan.components.ItemChat


@Composable
fun ResponLaporanScreen(
    onBack: () -> Unit, title: String ="Terdapat Pipa PDAM bocor di dekat balai RT Arjowinangun",
) {
    val sampleChat = listOf(
        Chat("Ruli Waskito", 'R', "Haylooo", "Pelapor", "27 Oktober 2024, 12:08", Color(0xFF94A3B8)),
        Chat("Ruli Waskito", 'R', "Bagaimana Kelanjutan Prosesnya Sudah 7 Hari tidak ada kabar", "Pelapor", "27 Oktober 2024, 12:08", Color(0xFF94A3B8)),
        Chat("Wahidin Sudirohusodo", 'W', "Baik Akan Segera kami proses, tunggu respon kami selanjutnya untuk hasil tindak lanjutnya", "Pengurus", "19 Oktober 2024, 19:32", Color(0xFF34D399))
    )
    Scaffold(
        containerColor = colorResource(R.color.white),
        topBar = {
            TopBarLaporan(
                title = "Respon Laporan",
                colors = Color.White,
                showLeadingIconBorder = true,
                showTrailingIconBorder = true,
                trailingIconType = TrailingIconType.SETTING,
                leadingIconType = LeadingIconType.BACK,
                onLeadingIconClick = onBack
            )
        },
        bottomBar = {
            ChatInputField(
                onSendClick = {  },
                onAttachClick = { }
            )
        }
    ) { innerPadding ->
        Card(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(Dimen.Padding.normal),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(Dimen.Padding.normal)
            ) {
                Spacer(modifier = Modifier.height(Dimen.Padding.normal))
                Text(
                    text = "Laporan",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    sampleChat.forEach { chat ->
                        ItemChat(chat = chat)
                    }
                }
            }
        }
    }
}

