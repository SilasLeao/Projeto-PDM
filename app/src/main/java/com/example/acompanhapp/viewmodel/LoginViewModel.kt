package com.example.acompanhapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.model.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {

    private val userPrefs = UserPreferences(context)

    // Estados do Login
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _senha = MutableStateFlow("")
    val senha: StateFlow<String> = _senha

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onSenhaChange(newSenha: String) {
        _senha.value = newSenha
    }

    fun login() {
        _isLoading.value = true
        RetrofitClient.getClient().getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val usuarios = response.body()?.data ?: emptyList()
                    val usuarioEncontrado = usuarios.find { it.email == _email.value }

                    if (usuarioEncontrado != null &&
                        BCrypt.checkpw(_senha.value, usuarioEncontrado.password)) {

                        viewModelScope.launch {
                            userPrefs.saveUser(
                                email = usuarioEncontrado.email,
                                name = usuarioEncontrado.nome,
                                id = usuarioEncontrado.id
                            )
                        }
                        _loginSuccess.value = true
                    } else {
                        _errorMessage.value = "Login e/ou senha incorretos"
                    }
                } else {
                    _errorMessage.value = "Erro na resposta: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Erro na conexão: ${t.message}"
            }
        })
    }
}
