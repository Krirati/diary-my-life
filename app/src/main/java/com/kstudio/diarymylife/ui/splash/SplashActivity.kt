package com.kstudio.diarymylife.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.kstudio.diarymylife.databinding.ActivitySplashBinding
import com.kstudio.diarymylife.ui.base.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        bindingEvent()
    }

//    private fun bindingEvent() = with(binding) {
//        rive.apply {
//            registerListener(listener)
//        }
//
//    }

//    val listener = object : RiveArtboardRenderer.Listener {
//        override fun notifyPlay(animation: PlayableInstance) {/* Do nothing*/
//        }
//
//        override fun notifyStateChanged(
//            stateMachineName: String,
//            stateName: String
//        ) {/* Do nothing*/
//        }
//
//        override fun notifyPause(animation: PlayableInstance) {/* Do nothing*/
//        }
//
//        override fun notifyStop(animation: PlayableInstance) {
//            navigateToActivity(MainActivity::class.java)
//        }
//
//        override fun notifyLoop(animation: PlayableInstance) {/* Do nothing*/
//        }
//    }
}