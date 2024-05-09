package com.alanturing.cpifp.datastore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alanturing.cpifp.datastore.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val viewModel:RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        splashScreen.setKeepOnScreenCondition { true }
        lifecycleScope.launch {
            viewModel.isRegistered()
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when(it) {
                        is RegisterUiState.Loading-> {}
                        is RegisterUiState.Success-> {
                            val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is RegisterUiState.Error ->{
                            val intent:Intent = Intent(this@SplashScreenActivity,RegisterActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

            }

        }

    }
}