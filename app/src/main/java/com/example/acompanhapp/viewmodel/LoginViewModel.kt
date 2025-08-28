package com.example.acompanhapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.UserApi
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.dao.UserDao
import com.example.acompanhapp.model.UserResponse
import com.example.acompanhapp.model.entity.UserEntity
import com.example.acompanhapp.viewmodel.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val api: UserApi,
    private val userPrefs: UserPreferences,
    private val userDao: UserDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onSenhaChange(senha: String) {
        _uiState.value = _uiState.value.copy(senha = senha)
    }

    fun login(onSuccess: () -> Unit) {
        val email = _uiState.value.email
        val senha = _uiState.value.senha

        _uiState.value = _uiState.value.copy(isLoading = true)

        api.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                if (response.isSuccessful) {
                    val usuarios = response.body()?.data ?: emptyList()
                    val usuarioEncontrado = usuarios.find { it.email == email }

                    if (usuarioEncontrado != null && BCrypt.checkpw(senha, usuarioEncontrado.password)) {
                        viewModelScope.launch {
                            // Salvar no Room
                            val userEntity = UserEntity(
                                id = usuarioEncontrado.id ?: "",
                                email = usuarioEncontrado.email ?: "",
                                nome = usuarioEncontrado.nome ?: "",
                                username = usuarioEncontrado.username ?: "",
                                password = usuarioEncontrado.password ?: ""
                            )
                            userDao.insertUser(userEntity)

                            onSuccess()
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(errorMessage = "Login e/ou senha incorretos")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = "Erro na resposta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _uiState.value = _uiState.value.copy(errorMessage = "Erro na conexão: ${t.message}", isLoading = false)
            }
        })
    }

}
