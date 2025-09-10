package com.ulud.laporan_ewarga.domain.model

import androidx.compose.ui.graphics.Color

data class Chat(
    val author: String,
    val initial: Char,
    val text: String,
    val role: String,
    val timestamp: String,
    val avatarColor: Color,
)
