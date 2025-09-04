package com.ulud.laporan_ewarga.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun PrimaryOutlinedButton(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val borderColor = if (enabled) {
        MaterialTheme.colorScheme.secondary
    } else {
        Color.Gray.copy(alpha = 0.5f)
    }
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(40.dp),
        enabled = enabled,
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = borderColor),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                leadingIcon()
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            }

            Text(
                text = text.uppercase(Locale.getDefault()),
                fontWeight = fontWeight
            )

            if (trailingIcon != null) {
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                trailingIcon()
            }
        }
    }
}