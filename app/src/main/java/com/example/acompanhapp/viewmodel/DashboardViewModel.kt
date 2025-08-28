package com.example.acompanhapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.model.Paciente
import com.example.acompanhapp.model.PacienteResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(private val context: Context) : ViewModel() {

    private val userPrefs = UserPreferences(context)

    private val _nomeUsuario = MutableStateFlow<String?>(null)
    val nomeUsuario: StateFlow<String?> = _nomeUsuario

    private val _idUsuario = MutableStateFlow<String?>(null)
    val idUsuario: StateFlow<String?> = _idUsuario

    private val _pacientesFiltrados = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientesFiltrados: StateFlow<List<Paciente>> = _pacientesFiltrados

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _nomeUsuario.value = userPrefs.getName()
            _idUsuario.value = userPrefs.getId()
            fetchPacientes()
        }
    }

    private fun fetchPacientes() {
        val userId = _idUsuario.value ?: return
        RetrofitClient.getClient().getPacientes().enqueue(object : Callback<PacienteResponse> {
            override fun onResponse(call: Call<PacienteResponse>, response: Response<PacienteResponse>) {
                if (response.isSuccessful) {
                    val todosPacientes = response.body()?.data ?: emptyList()
                    _pacientesFiltrados.value = todosPacientes.filter { it.familiaresId?.contains(userId) == true }
                }
            }

            override fun onFailure(call: Call<PacienteResponse>, t: Throwable) {
                Log.e("Dashboard", "Erro ao buscar pacientes: ${t.message}")
            }
        })
    }
}
