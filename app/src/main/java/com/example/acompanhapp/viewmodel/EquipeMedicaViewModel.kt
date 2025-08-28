package com.example.acompanhapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.model.EquipeMedicaResponse
import com.example.acompanhapp.viewmodel.state.EquipeMedicaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EquipeMedicaViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(EquipeMedicaUiState())
    val uiState: StateFlow<EquipeMedicaUiState> = _uiState

    fun loadMedicos() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        RetrofitClient.getClient().getEquipeMedica().enqueue(object : Callback<EquipeMedicaResponse> {
            override fun onResponse(call: Call<EquipeMedicaResponse>, response: Response<EquipeMedicaResponse>) {
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(medicos = response.body()?.data ?: emptyList(), isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = "Erro na resposta: ${response.code()}", isLoading = false)
                }
            }

            override fun onFailure(call: Call<EquipeMedicaResponse>, t: Throwable) {
                _uiState.value = _uiState.value.copy(errorMessage = "Erro na conexão: ${t.message}", isLoading = false)
                Log.e("EquipeMedica", t.message ?: "")
            }
        })
    }
}