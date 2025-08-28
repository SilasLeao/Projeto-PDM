package com.example.acompanhapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.UserApi
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.model.Paciente
import com.example.acompanhapp.model.PacienteResponse
import com.example.acompanhapp.viewmodel.state.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(
    private val api: UserApi,
    private val userPrefs: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    fun loadPacientes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val nome = userPrefs.getName()
            val id = userPrefs.getId()
            _uiState.value = _uiState.value.copy(nomeUsuario = nome)

            id?.let { userId ->
                api.getPacientes().enqueue(object : Callback<PacienteResponse> {
                    override fun onResponse(
                        call: Call<PacienteResponse>,
                        response: Response<PacienteResponse>
                    ) {
                        if (response.isSuccessful) {
                            val pacientesFiltrados = response.body()?.data?.filter { it.familiaresId?.contains(userId) == true } ?: emptyList()
                            _uiState.value = _uiState.value.copy(pacientes = pacientesFiltrados, isLoading = false)
                        } else {
                            _uiState.value = _uiState.value.copy(errorMessage = "Erro: ${response.code()}", isLoading = false)
                        }
                    }

                    override fun onFailure(call: Call<PacienteResponse>, t: Throwable) {
                        _uiState.value = _uiState.value.copy(errorMessage = t.message, isLoading = false)
                    }
                })
            }
        }
    }
}
