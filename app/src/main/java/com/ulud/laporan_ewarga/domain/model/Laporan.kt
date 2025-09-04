package com.ulud.laporan_ewarga.domain.model

import java.util.UUID

data class Laporan(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val category: String,
    val status: String,
    val photos: List<String> = emptyList(),
    val description: String,
    val isDraft: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)