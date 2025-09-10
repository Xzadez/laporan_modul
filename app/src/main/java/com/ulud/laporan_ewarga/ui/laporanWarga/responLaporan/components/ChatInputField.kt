package com.ulud.laporan_ewarga.ui.laporanWarga.responLaporan.components

import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.size.Dimension
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen

@Composable
fun ChatInputField(
    modifier: Modifier = Modifier,
    onSendClick: (String) -> Unit,
    onAttachClick: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    val isSendEnabled = text.isNotBlank()

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.Padding.medium, vertical = Dimen.Padding.normal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Tanggapi laporan ini...") },
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    IconButton(onClick = onAttachClick) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(R.drawable.attach_file),
                            contentDescription = "Lampirkan File"
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )

            Spacer(Modifier.width(2.dp))

            Button(
                onClick = {
                    if (isSendEnabled) {
                        onSendClick(text)
                        text = ""
                    }
                },
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Kirim Tanggapan",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}