package com.kstudio.diarymylife.widgets.custom_chart

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class BarAnimation : Animation() {
    private var progressBar: ProgressBar? = null
    private var from = 0f
    private var to = 0f

    companion object {
        fun newInstance(
            progressBar: ProgressBar,
            from: Float,
            to: Float
        ) = BarAnimation().apply {
            this.progressBar = progressBar
            this.from = from
            this.to = to
        }
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        progressBar?.progress = value.toInt()
    }
}
