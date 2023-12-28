package com.kstudio.diarymylife.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object FileUtility {
    fun getPhotoDirectory(context: Context): File {
        return File(context.filesDir, "photo").apply {
            if (!exists()) {
                mkdir()
            }
        }
    }

    fun getUriImage(context: Context, fileName: String): Uri {
        val photoFile = File(getPhotoDirectory(context), fileName)
        return Uri.fromFile(photoFile)
    }

    fun copyPhotoToInternalStorage(
        context: Context,
        fileName: String,
        uri: Uri?
    ): Uri? {
        if (uri == null) return null

        val directory = getPhotoDirectory(context)
        val file = File(directory, fileName)
        val outputStream = FileOutputStream(file)
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        inputStream.use {
            outputStream.use {
                inputStream.copyTo(outputStream)
            }
        }
        return Uri.fromFile(file)
    }
}
