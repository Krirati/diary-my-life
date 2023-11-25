package com.kstudio.diarymylife.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object Permissions {
    fun requirePermissionNotification(
        context: Context,
        permission: String,
        callBack: () -> Unit,
        requireAccept: () -> Unit
    ) {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
        if (isGranted) {
            callBack()
        } else {
            requireAccept()
        }
    }
}