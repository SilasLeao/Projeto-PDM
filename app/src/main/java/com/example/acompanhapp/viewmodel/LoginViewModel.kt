package com.example.acompanhapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.model.UserResponse
import com.example.acompanhapp.viewmodel.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val userPrefs = UserPreferences(context)

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onSenhaChange(senha: String) {
        _uiState.value = _uiState.value.copy(senha = senha)
    }

    fun login() {
        val email = _uiState.value.email
        val senha = _uiState.value.senha

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        RetrofitClient.getClient().getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                if (response.isSuccessful) {
                    val usuario = response.body()?.data?.find { it.email == email }
                    if (usuario != null && BCrypt.checkpw(senha, usuario.password)) {
                        // Login correto
                        viewModelScope.launch {
                            userPrefs.saveUser(usuario.email, usuario.nome, usuario.id)
                        }
                        _uiState.value = _uiState.value.copy(loginSuccess = true)
                    } else {
                        _uiState.value = _uiState.value.copy(errorMessage = "Login ou senha incorretos")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = "Erro na resposta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Erro na conexão: ${t.message}")
            }
        })
    }
}
