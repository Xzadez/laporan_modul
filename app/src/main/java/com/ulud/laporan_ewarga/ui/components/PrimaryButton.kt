package com.ulud.laporan_ewarga.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.R
import java.util.Locale

@Composable
fun PrimaryButton(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
        modifier = modifier
            .height(40.dp)
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
                text = text,
                fontWeight = fontWeight
            )

            if (trailingIcon != null) {
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                trailingIcon()
            }
        }
    }
}