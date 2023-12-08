package com.kstudio.diarymylife.ui.splash

sealed class Navigator {
    object Home: Navigator()
    object Onboarding: Navigator()
}
