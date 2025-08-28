package com.example.acompanhapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acompanhapp.model.entity.VisitaEntity

@Dao
interface VisitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisita(visita: VisitaEntity)

    @Query("SELECT * FROM visitas WHERE pacienteId = :pacienteId ORDER BY dataHora ASC")
    suspend fun getVisitasByPaciente(pacienteId: String): List<VisitaEntity>
}
