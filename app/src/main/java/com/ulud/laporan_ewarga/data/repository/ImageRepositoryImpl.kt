package com.ulud.laporan_ewarga.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.ulud.laporan_ewarga.domain.repository.ImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageRepository {

    override fun createTemporaryImageUri(): Uri? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "laporan_$timeStamp"

        val imageFile = File(context.cacheDir, "$fileName.jpg").apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }

    override fun copyImageToInternalStorage(sourceUri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(sourceUri) ?: return null

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "laporan_$timeStamp.jpg"
            val destinationFile = File(context.cacheDir, fileName).apply {
                createNewFile()
                deleteOnExit()
            }

            val outputStream = destinationFile.outputStream()
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                destinationFile
            )
        } catch (e: Exception) {
            null
        }
    }
}