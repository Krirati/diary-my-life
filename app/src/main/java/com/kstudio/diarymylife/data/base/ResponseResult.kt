package com.kstudio.diarymylife.data.base


sealed class Response {
    data class Success(val result: Long) : Response()
    object Failed : Response()

    fun Long.toResult(): Response {
        return if (this >= 0) {
            Success(result = this)
        } else {
            Failed
        }
    }
}
