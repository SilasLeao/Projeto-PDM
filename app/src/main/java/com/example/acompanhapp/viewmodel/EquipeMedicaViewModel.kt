package com.example.acompanhapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.UserApi
import com.example.acompanhapp.model.Medico
import com.example.acompanhapp.model.EquipeMedicaResponse
import com.example.acompanhapp.viewmodel.state.EquipeMedicaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EquipeMedicaViewModel(private val api: UserApi) : ViewModel() {

    private val _uiState = MutableStateFlow(EquipeMedicaUiState())
    val uiState: StateFlow<EquipeMedicaUiState> = _uiState

    fun loadMedicos() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        api.getEquipeMedica().enqueue(object : Callback<EquipeMedicaResponse> {
            override fun onResponse(call: Call<EquipeMedicaResponse>, response: Response<EquipeMedicaResponse>) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    medicos = response.body()?.data ?: emptyList(),
                    errorMessage = if (response.isSuccessful) null else "Erro: ${response.code()}"
                )
            }

            override fun onFailure(call: Call<EquipeMedicaResponse>, t: Throwable) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = t.message)
            }
        })
    }
}
