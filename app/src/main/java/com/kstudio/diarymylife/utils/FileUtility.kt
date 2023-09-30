package com.kstudio.diarymylife.utils

import android.content.Context
import android.net.Uri
import java.io.File

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
}