package com.alanturing.cpifp.datastore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alanturing.cpifp.datastore.R
import com.alanturing.cpifp.datastore.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModel:RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.register(binding.phoneInput.text.toString())
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is RegisterUiState.Loading -> {
                            binding.phoneLabel.error = null
                        }
                        is RegisterUiState.Error -> {
                            binding.phoneLabel.error ="El usuario ya existe"
                        }
                        RegisterUiState.Success -> {
                            binding.phoneLabel.error = null
                            val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}