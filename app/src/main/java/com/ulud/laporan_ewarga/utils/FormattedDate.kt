package com.ulud.laporan_ewarga.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun FormattedDate(timestamp: Long): String {
    if (timestamp == 0L) return ""

    val date = Date(timestamp)
    val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    return format.format(date)
}