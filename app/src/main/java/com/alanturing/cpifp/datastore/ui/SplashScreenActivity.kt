package com.alanturing.cpifp.datastore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            viewModel.isRegistered()
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when(it) {
                        is RegisterUiState.Loading-> {}
                        is RegisterUiState.Success-> {
                            val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                            startActivity(intent)
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