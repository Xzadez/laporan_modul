package com.ulud.laporan_ewarga.ui.laporanWarga.responLaporan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.domain.model.Chat
import com.ulud.laporan_ewarga.ui.Dimen

@Composable
fun ItemChat(chat: Chat) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(chat.avatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chat.initial.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = Dimen.FontSize.normal
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = chat.author,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chat.text,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${chat.role} - ${chat.timestamp}",
                color = Color.Gray,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}