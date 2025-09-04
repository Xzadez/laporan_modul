package com.ulud.laporan_ewarga.domain.repository

import android.net.Uri

interface ImageRepository {
    fun createTemporaryImageUri(): Uri?
}