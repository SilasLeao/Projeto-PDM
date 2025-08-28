package com.example.acompanhapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.model.Medico
import com.example.acompanhapp.model.EquipeMedicaResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

class EquipeMedicaViewModel(private val context: Context) : ViewModel() {

    private val _medicos = MutableStateFlow<List<Medico>>(emptyList())
    val medicos: StateFlow<List<Medico>> = _medicos

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchEquipeMedica()
    }

    private fun fetchEquipeMedica() {
        _isLoading.value = true
        RetrofitClient.getClient().getEquipeMedica().enqueue(object : Callback<EquipeMedicaResponse> {
            override fun onResponse(call: Call<EquipeMedicaResponse>, response: Response<EquipeMedicaResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _medicos.value = response.body()?.data ?: emptyList()
                } else {
                    Toast.makeText(context, "Erro na resposta: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EquipeMedicaResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "Erro na conexão: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
