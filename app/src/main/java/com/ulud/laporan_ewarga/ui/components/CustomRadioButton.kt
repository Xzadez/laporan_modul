package com.ulud.laporan_ewarga.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.R

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Color.Gray.copy(alpha = 0.6f)
) {
    val color by animateColorAsState(
        targetValue = if (selected) selectedColor else unselectedColor,
        animationSpec = tween(durationMillis = 200),
        label = "RadioButtonColor"
    )

    Canvas(
        modifier = modifier
            .clip(CircleShape)
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton
            )
            .size(24.dp)
    ) {
        val strokeWidth = 1.dp.toPx()

        drawCircle(
            color = color,
            radius = (size.minDimension / 2) - (strokeWidth / 2),
            style = if (selected) Fill else Stroke(width = strokeWidth)
        )

        if (selected) {
            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx(),
                style = Fill
            )
        }
    }
}