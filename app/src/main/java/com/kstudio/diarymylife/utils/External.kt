package com.kstudio.diarymylife.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

object External {
    fun openExternal(context: Context, uri: String) {
        kotlin.runCatching {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(context, browserIntent, null)
        }
    }
}