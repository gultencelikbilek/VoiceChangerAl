package com.okation.aivideocreator.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.okation.aivideocreator.databinding.ActivitySplashScreenBinding
import com.okation.aivideocreator.utils.Constants
import com.okation.aivideocreator.utils.StringConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBarSplashScreen.max = 1000
        runBlocking {
            ObjectAnimator.ofInt(
                binding.progressBarSplashScreen,
                StringConstants.progress,
                Constants.progresDurationValue
            )
                .setDuration(Constants.duration)
                .start()
            handler.postDelayed({
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
            }, Constants.delay)
        }
    }
}