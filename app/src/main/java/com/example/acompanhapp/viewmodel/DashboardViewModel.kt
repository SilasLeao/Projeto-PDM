package com.example.acompanhapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acompanhapp.api.UserApi
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.dao.UserDao
import com.example.acompanhapp.db.AppDatabase
import com.example.acompanhapp.model.Paciente
import com.example.acompanhapp.model.PacienteResponse
import com.example.acompanhapp.model.entity.VisitaEntity
import com.example.acompanhapp.viewmodel.state.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(
    private val api: UserApi,
    private val userDao: UserDao,
    private val db: AppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    private val _visitasPorPaciente = MutableStateFlow<Map<String, List<VisitaEntity>>>(emptyMap())
    val visitasPorPaciente: StateFlow<Map<String, List<VisitaEntity>>> = _visitasPorPaciente

    fun loadPacientes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Buscar usuário logado no Room
            val loggedUser = userDao.getLoggedUser()
            val nome = loggedUser?.nome
            val id = loggedUser?.id
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

    fun agendarVisita(pacienteId: String, dataHora: String) {
        viewModelScope.launch {
            val usuarioId = userDao.getLoggedUser()?.id ?: return@launch
            val visita = VisitaEntity(
                pacienteId = pacienteId,
                usuarioId = usuarioId,
                dataHora = dataHora
            )
            db.visitaDao().insertVisita(visita)

            // Recarregar visitas imediatamente após salvar
            carregarVisitas(pacienteId)
        }
    }

    // Carregar todas as visitas de todos os pacientes logados
    fun carregarTodasVisitas(pacientes: List<Paciente>) {
        viewModelScope.launch {
            val mapaAtual = mutableMapOf<String, List<VisitaEntity>>()
            pacientes.forEach { paciente ->
                val visitas = db.visitaDao().getVisitasByPaciente(paciente.id ?: "")
                mapaAtual[paciente.id ?: ""] = visitas
            }
            _visitasPorPaciente.value = mapaAtual
        }
    }

    fun getVisitas(pacienteId: String, onResult: (List<VisitaEntity>) -> Unit) {
        viewModelScope.launch {
            val visitas = db.visitaDao().getVisitasByPaciente(pacienteId)
            onResult(visitas)
        }
    }

    fun carregarVisitas(pacienteId: String) {
        viewModelScope.launch {
            val visitas = db.visitaDao().getVisitasByPaciente(pacienteId)
            val mapaAtual = _visitasPorPaciente.value.toMutableMap()
            mapaAtual[pacienteId] = visitas
            _visitasPorPaciente.value = mapaAtual
        }
    }
}


