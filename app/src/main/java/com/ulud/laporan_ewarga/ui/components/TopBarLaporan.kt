package com.ulud.laporan_ewarga.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.Dimen

enum class LeadingIconType {
    MENU,
    BACK,
    CLOSE,
    NONE
}

enum class TrailingIconType {
    ADD,
    SETTING,
    NONE
}

@Composable
fun TopBarLaporan(
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    title: String = "",
    colors: Color = colorResource(R.color.white),
    leadingIconType: LeadingIconType = LeadingIconType.NONE,
    trailingIconType: TrailingIconType = TrailingIconType.NONE,
    showLeadingIconBorder: Boolean = false,
    showTrailingIconBorder: Boolean = false,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(colors),
        elevation = elevation,
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Padding.normal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val border = BorderStroke(1.dp, Color.LightGray.copy(0.6f))
                when (leadingIconType) {
                    LeadingIconType.MENU -> {
                        IconButton(
                            modifier = Modifier
                                .background(Color.White, CircleShape)
                                .size(Dimen.IconSize.large)
                                .then(
                                    if (showLeadingIconBorder) Modifier.border(
                                        border,
                                        CircleShape
                                    ) else Modifier
                                )
                                .padding(Dimen.Padding.extraSmall),
                            onClick = onLeadingIconClick
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.size(Dimen.IconSize.medium)
                            )
                        }
                    }

                    LeadingIconType.BACK -> {
                        BackButton(
                            modifier = Modifier
                                .then(
                                    if (showLeadingIconBorder) Modifier.border(
                                        border,
                                        CircleShape
                                    ) else Modifier
                                ),
                            onBack = onLeadingIconClick
                        )
                    }

                    LeadingIconType.CLOSE -> {
                        IconButton(
                            modifier = Modifier
                                .size(Dimen.IconSize.large)
                                .then(
                                    if (showLeadingIconBorder) Modifier.border(
                                        border,
                                        CircleShape
                                    ) else Modifier
                                ),
                            onClick = onLeadingIconClick
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                modifier = Modifier.size(Dimen.IconSize.medium)
                            )
                        }
                    }

                    LeadingIconType.NONE -> {
                        if (trailingIconType == TrailingIconType.NONE) {
                            Spacer(modifier = Modifier.size(Dimen.IconSize.large))
                        }
                    }
                }

                if (leadingIconType != LeadingIconType.NONE) {
                    Spacer(Modifier.width(Dimen.Padding.medium))
                }

                Text(
                    text = title,
                    color = colorResource(R.color.textPrimary),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            val border = BorderStroke(1.dp, Color.LightGray.copy(0.6f))

            when (trailingIconType) {
                TrailingIconType.ADD -> {
                    IconButton(
                        modifier = Modifier
                            .background(Color.White, CircleShape)
                            .size(Dimen.IconSize.large)
                            .then(
                                if (showTrailingIconBorder) Modifier.border(
                                    border,
                                    CircleShape
                                ) else Modifier
                            )
                            .padding(Dimen.Padding.extraSmall),
                        onClick = onTrailingIconClick
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Plus",
                            modifier = Modifier.size(Dimen.IconSize.medium)
                        )
                    }
                }

                TrailingIconType.SETTING -> {
                    IconButton(
                        modifier = Modifier
                            .background(Color.White, CircleShape)
                            .size(Dimen.IconSize.large)
                            .then(
                                if (showTrailingIconBorder) Modifier.border(
                                    border,
                                    CircleShape
                                ) else Modifier
                            )
                            .padding(Dimen.Padding.extraSmall),
                        onClick = onTrailingIconClick
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Setting",
                            modifier = Modifier.size(Dimen.IconSize.medium)
                        )
                    }
                }

                TrailingIconType.NONE -> {
                    if (leadingIconType == LeadingIconType.NONE) {
                        Spacer(modifier = Modifier.size(Dimen.IconSize.large))
                    }
                }
            }
        }
    }
}