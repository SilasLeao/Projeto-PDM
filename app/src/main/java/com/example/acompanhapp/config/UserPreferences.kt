package com.example.acompanhapp.config

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Extensão para facilitar o acesso ao DataStore
val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {

    // Chaves para identificar/resgatar valores armazenados
    companion object {
        val EMAIL_KEY = stringPreferencesKey("email")
        val NAME_KEY = stringPreferencesKey("name")
        val ID_KEY = stringPreferencesKey("id")
    }


    // Salva os dados do usuário no DataStore.
    suspend fun saveUser(email: String?, name: String?, id: String?) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email as String
            prefs[NAME_KEY] = name as String
            prefs[ID_KEY] = id as String
        }
    }

    suspend fun getName(): String? {
        return context.dataStore.data
            .map { it[NAME_KEY] }
            .first()
    }

    suspend fun getId(): String? {
        return context.dataStore.data
            .map { it[ID_KEY] }
            .first()
    }

}
