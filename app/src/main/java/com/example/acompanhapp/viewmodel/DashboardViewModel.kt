package com.example.acompanhapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.model.PacienteResponse
import com.example.acompanhapp.viewmodel.state.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    private val userPrefs = UserPreferences(context)

    fun loadPacientes() {
        viewModelScope.launch {
            val nome = userPrefs.getName()
            val id = userPrefs.getId()
            _uiState.value = _uiState.value.copy(nomeUsuario = nome, idUsuario = id)

            id?.let { userId ->
                RetrofitClient.getClient().getPacientes().enqueue(object : Callback<PacienteResponse> {
                    override fun onResponse(call: Call<PacienteResponse>, response: Response<PacienteResponse>) {
                        if (response.isSuccessful) {
                            val filtrados = response.body()?.data?.filter { it.familiaresId?.contains(userId) == true } ?: emptyList()
                            _uiState.value = _uiState.value.copy(pacientes = filtrados, isLoading = false)
                        } else {
                            _uiState.value = _uiState.value.copy(errorMessage = "Erro na resposta: ${response.code()}", isLoading = false)
                        }
                    }

                    override fun onFailure(call: Call<PacienteResponse>, t: Throwable) {
                        _uiState.value = _uiState.value.copy(errorMessage = "Erro na conexão: ${t.message}", isLoading = false)
                        Log.e("Dashboard", "Erro ao buscar pacientes: ${t.message}")
                    }
                })
            }
        }
    }
}