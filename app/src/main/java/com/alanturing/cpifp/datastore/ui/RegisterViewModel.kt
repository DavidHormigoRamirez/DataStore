package com.alanturing.cpifp.datastore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturing.cpifp.datastore.data.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterUiState {
    object Loading:RegisterUiState()
    class Error(val message:String):RegisterUiState()
    object Success:RegisterUiState()
}
@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository):ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Loading)
    val uiState:StateFlow<RegisterUiState>
        get() = _uiState.asStateFlow()

    suspend fun isRegistered() {
        viewModelScope.launch {
            _uiState.value =  if (repository.isRegistered())
                RegisterUiState.Success
            else
                RegisterUiState.Error("no hay usuario local")
        }
    }

    suspend fun register(phone:String) {
        viewModelScope.launch {
            _uiState.value = if (repository.register(phone))
                RegisterUiState.Success
            else
                RegisterUiState.Error("usuario ya existe")

        }
    }
}